# Marine Mesh: Smart Nodes and Radar Hardware (2026-10-18)

## Corrected product direction
The **browser GUI is a first-class smart node** — not just a remotely served test display. No Raspberry Pi, no Exodus port, and no assumption that an Android tablet must be a permanent central server.

Node types:
- **Web node**: browser on desktop/tablet/phone; independently selects radar, AIS, navigation, instrumentation, anchor view; local preferences, analytics, UI and playback. With secure networking it can participate in coordination and simulation.
- **Android node**: native APK with the same experience, plus native networking, USB-Ethernet, background execution and hardware integrations where supported.
- **Radar gateway node**: any capable device attached to scanner Ethernet; receives vendor radar datagrams, normalizes radar spokes, publishes authenticated telemetry. May be Android with USB Ethernet, Linux/Windows PC, or purpose-built appliance; no Pi required.
- **Sensor node**: GPS, AIS, NMEA instruments; sources are separate from viewers.

### Network design (implementation targets, not completed features)
- Transport: WSS control/telemetry with a discoverable coordinator or gateway; native P2P via WebRTC DataChannel is an option for browser-to-browser data after signaling. A pure browser cannot listen for arbitrary Ethernet multicast/UDP.
- Typed protocol: versioned Protobuf or equivalent with source timestamps, data quality, stale-data indicators.
- Independent per-node layouts, range *view* zoom and overlays. Scanner range/power are shared physical hardware controls, subject to privileged ownership and interlocks.
- Authenticated pairing; identity and explicit control permissions; reconnect state and provenance.
- Consensus is **not** assumed for control of a radar; a single hardware control lease/arbiter avoids conflicting commands.
- Browser simulation should be possible without installed mobile nodes; replay captured or synthetic fixtures to all roles.
- No autonomous hardware transmit on discovery; only explicit verified human control.
- Browser hosting beyond pure static demo requires a live compatible signaling/relay endpoint or suitable device node; Vercel static hosting cannot itself ingest local radar Ethernet multicast.

### Hardware compatibility research
**Recommended first adapter: Navico/Simrad HALO24**, or 4G for low-cost legacy development. The open radar_pi implementation lists Navico BR24/3G/4G, HALO 20/20+/24/4/6/8, Raymarine Quantum Q24C/Q24D, Garmin HD/xHD. Its license is GPL-2.0; no source incorporated here.
**Next: Raymarine Quantum Q24D**, wired Ethernet favored over proprietary Wi-Fi handshake.
**SDK route: Furuno DRS Ethernet**, e.g. DRS2DNXT: Furuno officially advertises Linux/Windows SDKs, including Linux ARM, but not Android; licensing/commercial terms must be checked.
**Reference: mayara-server** advertises tested Navico, Raymarine and some Furuno models, Apache-2.0 license. Consult as documentation/compatibility evidence, not as adopted code.

Research sources:
- https://github.com/opencpn-radar-pi/radar_pi
- https://github.com/MarineYachtRadar/mayara-server
- https://www.furuno.com/en/support/sdk/index.html
- https://github-wiki-see.page/m/opencpn-radar-pi/radar_pi/wiki/Hardware-installation-%28Raymarine%29

### Acceptance gates
1. Static hosted GUI: independently switch smart-node roles and run deterministic replay/simulation in a browser. Verify mobile layout and console errors.
2. Two separate browser instances receive synchronized replay telemetry over real LAN relay/signaling; role choices remain independent.
3. Android and browser joined in the same session; disconnect, reconnect, stale-data and privilege tests.
4. Radar Ethernet capture on selected real scanner: document physical cabling, protocols, discovery, sample packets and decoder output; never claim compatibility from third-party support tables alone.
5. Integration tests and video/screenshots with GitHub Actions artifacts; build and deploy statuses must be verifiable.
6. Navigation safety: software experimental; not a replacement for approved equipment or lookout.

## Current state
Previous Marine Mesh scaffold has an Android WebView and a read-only simulated WebSocket service. It is **not yet** the browser-as-smart-node architecture above. No hardware verified. Do not present this planning document as a completed implementation.
