<script>
  export let spot;
  let address = "";

  // Latitude and Longitude of the location you want to reverse geocode
  const lat = spot.latitude; // Example latitude
  const lng = spot.longitude; // Example longitude

  // Create a Nominatim API request URL
  const nominatimUrl = `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lng}`;

  // Make a request to the Nominatim API
  fetch(nominatimUrl)
    .then((response) => response.json())
    .then((data) => {
      address = data.display_name;
      console.log(`${address}`);
    })
    .catch((error) => {
      console.error("Error:", error);
    });
</script>

<main />

<style></style>
