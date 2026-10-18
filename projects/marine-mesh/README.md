# Marine Mesh — Android distributed marine displays

Independent clean-room starter concept. No Raspberry Pi, Python backend, Exodus source code, or copied radar assets.

## Design
- One Android APK, selectable device roles: Main, Radar, Anchor Watch, Instruments.
- Android device as coordinator; LAN peers subscribe to a shared telemetry feed.
- Kotlin Android shell and local WebSocket broadcast; React/TypeScript/Vite visualization.
- GitHub Actions for build verification, not onboard operation.
- Independent display preferences and zoom per device.
- Future work: real radar ingress, AIS/NMEA, GPS, pairing, authentication, discovery, foreground service, failover, and sensor stale-data alerts.

## Status
**Prototype / simulation only.** No real radar decoding or navigation hardware is integrated. NOT FOR NAVIGATION OR COLLISION AVOIDANCE.

The earlier standalone starter archive is available in the originating ChatGPT conversation. This branch is the canonical project workspace and should receive verified source imports and CI validation before anyone installs an APK.

## Work rules
Do not merge into main without review. Keep changes isolated under projects/marine-mesh. Never report a working radar or Android APK without an associated built artifact and hardware test evidence.
