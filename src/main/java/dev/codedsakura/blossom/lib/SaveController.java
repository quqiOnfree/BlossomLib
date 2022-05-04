package dev.codedsakura.blossom.lib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.PersistentState;

import java.io.*;
import java.util.concurrent.atomic.AtomicReference;

import static dev.codedsakura.blossom.lib.BlossomLib.CONFIG;

public abstract class SaveController<T> extends PersistentState {
    protected static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .disableHtmlEscaping()
            .create();

    protected T data = null;

    public SaveController() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            data = readPreferred(server);
            if (data == null) {
                data = defaultData();
            }
            write(server);
        });
        ServerLifecycleEvents.SERVER_STOPPING.register(this::write);
    }

    public abstract T defaultData();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        markDirty();
        writeNBT(nbt);
        return nbt;
    }

    public abstract T readNBT(NbtCompound nbt);

    public abstract void writeNBT(NbtCompound nbt);

    public abstract T readJson(InputStreamReader reader);

    public abstract void writeJson(OutputStreamWriter writer);

    public abstract T readCSV(InputStreamReader reader);

    public abstract void writeCSV(OutputStreamWriter writer);

    public abstract String getFilename();

    private File getFile(MinecraftServer server, String extension) {
        return server.getSavePath(WorldSavePath.ROOT)
                .resolve("data")
                .resolve(getFilename() + "." + extension)
                .toFile();
    }

    private InputStreamReader getReader(MinecraftServer server, String extension) throws FileNotFoundException {
        return new InputStreamReader(new FileInputStream(this.getFile(server, extension)));
    }

    private OutputStreamWriter getWriter(MinecraftServer server, String extension) throws FileNotFoundException {
        return new OutputStreamWriter(new FileOutputStream(this.getFile(server, extension)));
    }


    public T readPreferred(MinecraftServer server) {
        T data = read(server, CONFIG.storageMedium);
        if (data != null) {
            return data;
        }

        for (StorageMedium medium : StorageMedium.values()) {
            if (medium.equals(CONFIG.storageMedium)) {
                continue;
            }

            data = read(server, medium);
            if (data != null) {
                if (!clear(server, medium)) {
                    BlossomLib.LOGGER.warn("Failed to clear " + medium);
                }
                return data;
            }
        }
        return null;
    }

    private void write(MinecraftServer server) {
        BlossomLib.LOGGER.trace("writing {}", CONFIG.storageMedium);
        try {
            switch (CONFIG.storageMedium) {
                case NBT -> {
                    server.getOverworld()
                            .getPersistentStateManager()
                            .set(getFilename(), this);
                    markDirty();
                }
                case CSV -> {
                    OutputStreamWriter writer = getWriter(server, "csv");
                    writeCSV(writer);
                    writer.close();
                }
                case JSON -> {
                    OutputStreamWriter writer = getWriter(server, "json");
                    writeJson(writer);
                    writer.close();
                }
            }
        } catch (IOException e) {
            BlossomLib.LOGGER.throwing(e);
        }
    }

    private T read(MinecraftServer server, StorageMedium medium) {
        BlossomLib.LOGGER.trace("reading {}", medium);
        try {
            return switch (medium) {
                case NBT -> getNbt(server);
                case CSV -> readCSV(getReader(server, "csv"));
                case JSON -> readJson(getReader(server, "json"));
            };
        } catch (Exception e) {
            BlossomLib.LOGGER.info(e.getMessage());
            return null;
        }
    }

    private boolean clear(MinecraftServer server, StorageMedium medium) {
        BlossomLib.LOGGER.trace("clearing {}", medium);
        if (medium == StorageMedium.NBT) {
            server.getOverworld()
                    .getPersistentStateManager()
                    .set(getFilename(), null);
        }

        File file = switch (medium) {
            case NBT -> getFile(server, "dat");
            case CSV -> getFile(server, "csv");
            case JSON -> getFile(server, "json");
        };
        return file.delete();
    }

    private T getNbt(MinecraftServer server) {
        AtomicReference<T> data = new AtomicReference<>();
        server.getOverworld()
                .getPersistentStateManager()
                .get(nbt -> {
                    data.set(readNBT(nbt));
                    return this;
                }, this.getFilename());
        return data.get();
    }
}
