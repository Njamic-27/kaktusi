<script>
  import { createEventDispatcher, onMount } from "svelte";
  import L, { marker } from "leaflet";
  import "leaflet/dist/leaflet.css";
  export let spots;

  // Define marker icons with different colors
  const redIcon = L.icon({
    iconUrl: "./src/public/markers/red_marker.png",
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowUrl: "./node_modules/leaflet/dist/images/marker-shadow.png",
    shadowSize: [41, 41],
  });

  const greenIcon = L.icon({
    iconUrl: "./src/public/markers/green_marker.png",
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowUrl: "./node_modules/leaflet/dist/images/marker-shadow.png",
    shadowSize: [41, 41],
  });

  const defaultIcon = L.icon({
    iconUrl: "./node_modules/leaflet/dist/images/marker-icon.png",
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowUrl: "./node_modules/leaflet/dist/images/marker-shadow.png",
    shadowSize: [41, 41],
  });

  const dispatch = createEventDispatcher();
  let selectedMarker = null;

  const latitude = 45.815;
  const longitude = 15.9819;
  const zoomLevel = 13;

  onMount(() => {
    const map = L.map("map").setView([latitude, longitude], zoomLevel);

    L.tileLayer("https://{s}.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png", {
      maxZoom: 19,
      attribution:
        '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, Tiles style by <a href="https://www.hotosm.org/" target="_blank">Humanitarian OpenStreetMap Team</a> hosted by <a href="https://openstreetmap.fr/" target="_blank">OpenStreetMap France</a>',
    }).addTo(map);
    spots.forEach((spot) => {
      let customIcon = spot.occupied ? redIcon : greenIcon;
      let markerData = L.marker([spot.latitude, spot.longitude], {
        icon: customIcon,
      }).addTo(map);
      markerData.on("click", () => {
        // Change the icon to green when clicked
        markerData.setIcon(defaultIcon);

        if (selectedMarker && selectedMarker !== markerData) {
          // Reset the icon of the previously selected marker to blue
          selectedMarker.setIcon(customIcon);
        }

        map.setView([spot.latitude, spot.longitude], 20);
        selectedMarker = markerData;
        dispatch("parkingSelect", spot);
      });
    });
  });
</script>

<div id="map" />

<style>
  #map {
    position: relative;
    z-index: 0;
    height: 100%;
    width: 100%;
  }
</style>
