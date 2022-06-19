package dev.codedsakura.blossom.lib.utils.gson;

import com.google.gson.*;
import dev.codedsakura.blossom.lib.utils.CubicBezierCurve;

import java.lang.reflect.Type;

public class CubicBezierCurveSerializer implements JsonDeserializer<CubicBezierCurve>, JsonSerializer<CubicBezierCurve> {
    @Override
    public CubicBezierCurve deserialize
            (JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        double[] values = new double[4];
        JsonArray valuesElement = jsonObject.get("values").getAsJsonArray();

        if (valuesElement.size() != 4) {
            throw new JsonParseException("CubicBezierCurve values must be exactly 4 elements long!");
        }

        for (int i = 0; i < 4; i++) {
            values[i] = valuesElement.get(i).getAsDouble();
        }

        double start = jsonObject.get("start").getAsDouble();
        double end = jsonObject.get("end").getAsDouble();
        int stepCount = jsonObject.get("stepCount").getAsInt();
        boolean enabled = jsonObject.get("enabled").getAsBoolean();

        return new CubicBezierCurve(values, start, end, stepCount, enabled);
    }

    @Override
    public JsonElement serialize(CubicBezierCurve cubicBezierCurve, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("enabled", cubicBezierCurve.isEnabled());

        JsonArray valuesList = new JsonArray();
        for (double point : cubicBezierCurve.getPoints()) {
            valuesList.add(point);
        }
        jsonObject.add("values", valuesList);

        jsonObject.addProperty("start", cubicBezierCurve.getStart());
        jsonObject.addProperty("end", cubicBezierCurve.getEnd());
        jsonObject.addProperty("stepCount", cubicBezierCurve.getStepCount());

        return jsonObject;
    }
}
