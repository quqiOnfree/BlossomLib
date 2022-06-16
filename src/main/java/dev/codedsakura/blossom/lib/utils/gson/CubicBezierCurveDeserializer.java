package dev.codedsakura.blossom.lib.utils.gson;

import com.google.gson.*;
import dev.codedsakura.blossom.lib.utils.CubicBezierCurve;

import java.lang.reflect.Type;

public class CubicBezierCurveDeserializer implements JsonDeserializer<CubicBezierCurve> {
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

        return new CubicBezierCurve(values, start, end, stepCount);
    }
}
