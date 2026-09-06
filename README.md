# 🧠 Quantum IQ & Neuro Scanner (Inescapable Audio Rickroll Prank) 🕺🔊

A complete, production-ready Android application built in Kotlin. Disguised as an authentic, high-tech cognitive scanner ("Quantum IQ & Neuro Scanner"), the app deceives the user into calibrating their optical sensors before instantly blasting the classic **Rickroll audio at 100% maximum volume on an infinite loop with NO PAUSE BUTTON!**

---

## 🔒 The Ultimate Trap Features

- **Deceptive Decoy Screen:**
  - Designed as an official cognitive evaluation tool (*"Certified Cognitive Labs • v4.2 / Mensa Benchmark Standard"*).
  - Subtle pulsing biometric radar sensor and age-group selectors.
- **Immediate Audio Explosion:**
  - Tapping **"CALIBRATE & START SCAN"** immediately hides the decoy interface.
  - Automatically queries `AudioManager` and maxes out `STREAM_MUSIC` volume to 100%.
  - Begins an **infinite audio loop** (`mediaPlayer.isLooping = true`).
- **NO PAUSE BUTTON:**
  - Zero pause, stop, or reset buttons exist on the prank screen.
- **Hardware Volume Lock:**
  - Intercepts physical volume down and mute buttons (`KEYCODE_VOLUME_DOWN`, `KEYCODE_VOLUME_MUTE`). If the victim tries to lower the volume, the app immediately overrides it and sets it back to 100% MAX!
- **Back Button Intercepted:**
  - The Android back button/gesture is intercepted and disabled (`onBackPressedDispatcher`).
- **Screen Locked Awake:**
  - Forces `FLAG_KEEP_SCREEN_ON` so the display does not turn off.
- **Only Way Out:**
  - Power off / restart the phone or force kill via recent apps!

---

## 📂 Project Directory Structure

```
rannk/
├── build.gradle.kts                 # Root project build configuration
├── settings.gradle.kts               # Module and repository management
├── gradle.properties                 # JVM and AndroidX optimization flags
├── README.md                         # Documentation & build instructions
├── web_preview/                      # Localhost interactive preview server
│   ├── server.js                     # Zero-dependency Node.js streaming server
│   └── index.html                    # Audio trap simulator
└── app/
    ├── build.gradle.kts              # App module build script (SDK 34)
    ├── proguard-rules.pro            # ProGuard rules
    └── src/main/
        ├── AndroidManifest.xml       # Permissions, themes, and activity definition
        ├── java/com/prank/rickroll/
        │   └── MainActivity.kt       # Trap logic, volume lock, infinite audio loop
        └── res/
            ├── drawable/             # Custom gradients, scan rings, and cards
            ├── layout/
            │   └── activity_main.xml # Decoy UI and Trapped Prank screen (NO PAUSE)
            ├── raw/
            │   └── rickroll.mp4      # Bundled audio track (22 MB Rickroll audio)
            └── values/
                ├── colors.xml        # High-tech sci-fi theme colors
                ├── strings.xml       # Decoy and prank text strings
                └── themes.xml        # Material 3 dark theme
```

---

## 🛠️ How to Build the APK in Android Studio

1. Launch **Android Studio**.
2. Click **Open** and select:
   ```
   c:\Users\admin\Music\rannk
   ```
3. Allow Gradle to sync.
4. In the top menu bar, click:
   ```
   Build > Build Bundle(s) / APK(s) > Build APK(s)
   ```
5. When the build completes, click **locate** in the bottom-right notification to find:
   ```
   app/build/outputs/apk/debug/app-debug.apk
   ```
6. Install on any Android device and hand it to your victim!
