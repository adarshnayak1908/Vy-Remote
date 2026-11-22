# Vy TV Remote

A clean, open-source Android app to control your Android TV or Google TV over Wi-Fi. Modified and maintained by **Vy**.

No ads, no tracking, and no bloat.

<p align="center">
  <img src="https://i.ibb.co/Gf8qMzpv/screenshots.webp" alt="Screenshots: the remote, typing into a TV search box, and voice search" width="560">
</p>

## Features

- **Android TV Remote Protocol v2**: Connects directly using Google's official TLS protocol with standard on-screen PIN pairing.
- **Network discovery**: Automatically finds TVs on your local network using mDNS.
- **Swipe and D-pad modes**: Swipe anywhere to navigate (default), or switch to a classic D-pad. Tap or hold for OK, with haptic feedback.
- **App shortcuts**: One-tap buttons that open YouTube, Netflix, Spotify and more on your TV. Add your own by package name or link.
- **Phone volume buttons**: Optionally control the TV volume with your phone's hardware keys.
- **Keyboard input**: Type directly into TV search and input fields from your phone.
- **Quick Settings tile**: Jump into the remote right from your Android notification shade.
- **Material 3 Expressive design**: Springy motion, shape-morphing buttons, a dot-field swipe pad and a volume rocker that shows the TV's level.
- **Themes**: Light, dark or system, an optional pure black theme for OLED screens, and wallpaper colours on Android 12+.

## How to Connect

1. Make sure your phone and TV are on the same Wi-Fi network.
2. Open the app and select your TV from the discovered devices.
3. Enter the 6-character PIN code displayed on your TV screen.

## Building

Prerequisites:
- Android Studio Ladybug or newer
- JDK 17
- Android SDK 37

```bash
git clone <your-repo-url>
cd TV-remote
./gradlew assembleDebug
```

The APK will be generated at `app/build/outputs/apk/debug/app-debug.apk`.

## Credits & Attributions

- Modified and maintained by **Vy**.
- Based on the open-source project by [souravkl11](https://github.com/souravkl11/android-tv-remote).
- App icons are from [Simple Icons](https://simpleicons.org) (CC0).

## License

Copyright (c) 2025 Vy.

This project is licensed under the Apache License 2.0. See [LICENSE](LICENSE) for details.

