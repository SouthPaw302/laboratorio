# Marine Mesh — GPS-first plan

Decision: build a GPS-first distributed marine app now; radar is an optional hardware node later. Browser and Android are first-class smart nodes, no Raspberry Pi or Exodus code.

Phase one: Android GNSS and browser geolocation (with HTTPS and consent), displaying own-ship position, speed-over-ground, course-over-ground, source, age, and accuracy. Track history and waypoints; anchor watch only when reliable background monitoring and alerts are implemented and tested. Nautical charts require appropriate licensing and local coverage.

Important: GPS cannot detect nearby boats, land obstacles, depth, or weather. AIS requires an AIS source. Do not label synthetic radar returns, depth, or wind as live sensor readings.

Browser node: full independent view, analytics and local preferences. Multi-node sharing needs an authenticated LAN relay / coordinator and message schema. Browser cannot directly receive arbitrary UDP Ethernet radar traffic.

Future hardware: optional AIS and radar gateways publish source-stamped data, independent from views. Hardware range/power controls require privileges and verification.

Acceptance tests: (1) live GPS vs DEMO distinction; (2) two browser nodes share one verified GNSS feed with independent role/layout; (3) Android GNSS permission and disconnected states; (4) real charts and background anchor-watch validation; (5) later hardware radar integration. Current code remains simulation only.
