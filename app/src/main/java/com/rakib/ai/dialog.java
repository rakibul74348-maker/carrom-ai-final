package com.rakib.ai;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class dialog {

    public static final String CONFIG_URL = "https://raw.githubusercontent.com/rakibul74348-maker/carrom-ai-final/main/licenses.json";

    public interface LicenseCallback {
        void onSuccess(String message);
        void onFailure(String error);
    }

    public static void verifyLicense(Context context, String inputKey, LicenseCallback callback) {
        new Thread(() -> {
            try {
                URL url = new URL(CONFIG_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                if (conn.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    JSONObject json = new JSONObject(response.toString());
                    if (json.has("keys")) {
                        JSONObject keys = json.getJSONObject("keys");
                        if (keys.has(inputKey)) {
                            JSONObject keyData = keys.getJSONObject(inputKey);
                            boolean isValid = keyData.optBoolean("valid", false);
                            if (isValid) {
                                SharedPreferences prefs = context.getSharedPreferences("vip_prefs", Context.MODE_PRIVATE);
                                prefs.edit().putBoolean("is_vip", true).apply();
                                callback.onSuccess("VIP License Activated Successfully!");
                                return;
                            }
                        }
                    }
                    callback.onFailure("Invalid or Expired License Key!");
                } else {
                    callback.onFailure("Server connection error: " + conn.getResponseCode());
                }
            } catch (Exception e) {
                callback.onFailure("Verification failed: " + e.getMessage());
            }
        }).start();
    }
                        }
