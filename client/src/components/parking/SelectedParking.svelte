<script>
  import { fade, fly, scale, slide } from "svelte/transition";
  import { createEventDispatcher, onMount } from "svelte";
  import { parkingApi } from "@/api";
  import { isAdmin } from "@/stores/auth";

  export let spot;
  let address = "";
  let displayCard = false;
  let price = 0;
  let displayAdminActions = false;

  let selectedZone = spot.parkingSpotZone; // Initialize with an empty value
  let selectedType = spot.parkingSpotType; // Initialize with an empty value

  if (isAdmin()) {
    displayAdminActions = true;
  }

  function handleZoneChange(event) {
    selectedZone = event.target.value;
  }

  // Function to handle changes in the selected type
  function handleTypeChange(event) {
    selectedType = event.target.value;
  }

  const zoneOptions = ["Zone1", "Zone2", "Zone3", "Zone4"];
  const typeOptions = ["NORMAL", "HANDICAP", "ELECTRIC"];

  let selectedHour = "00"; // Initialize with a default value
  let selectedMinute = "00"; // Initialize with a default value

  // Latitude and Longitude of the location you want to reverse geocode
  const lat = spot.latitude; // Example latitude
  const lng = spot.longitude; // Example longitude

  // Create a Nominatim API request URL
  const nominatimUrl = `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lng}`;
  onMount(async () => {
    const id = spot.id;
    price = await parkingApi.fetchPrice(id);

    // Make a request to the Nominatim API
    fetch(nominatimUrl)
      .then((response) => response.json())
      .then((data) => {
        address = `${data.address.road} ${
          data.address.house_number ? data.address.house_number : "BB"
        }`;
        displayCard = true;
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  });

  let dispatch = createEventDispatcher();

  function navigate() {
    const url = `https://www.google.com/maps?q=${lat},${lng}`;
    window.location.href = url;
  }

  const saveChangesAdmin = async () => {
    await parkingApi.update(spot.id, selectedZone, selectedType);
  };

  const deleteSpot = async () => {
    await parkingApi.deleteSpot(spot.id);
  };

  async function handleReservation() {
    let endH = selectedHour;
    let endM = selectedMinute;
    let parkingSpotId = spot.id;
    let response = await parkingApi.makeReservation(endH, endM, parkingSpotId);
    console.log("Stipe kaže");
    console.log(response);
    console.log("karla");
    dispatch("refresh");
  }
</script>

<main>
  {#if displayCard}
    <div class="cardContainer" in:slide={{ delay: 200 }} out:slide>
      <div class="spot-title">{address}</div>
      <p class="parking-type">Parking type: {spot.parkingSpotType}</p>
      <div class="time-container">
        <span class="time-label">End Time:</span>
        <div class="time-select">
          <select class="hour-select input-field" bind:value={selectedHour}>
            <option value="00">00</option>
            <option value="01">01</option>
            <option value="02">02</option>
            <option value="03">03</option>
            <option value="04">04</option>
            <option value="05">05</option>
            <option value="06">06</option>
            <option value="07">07</option>
            <option value="08">08</option>
            <option value="09">09</option>
            <option value="10">10</option>
            <option value="11">11</option>
            <option value="12">12</option>
            <option value="13">13</option>
            <option value="14">14</option>
            <option value="15">15</option>
            <option value="16">16</option>
            <option value="17">17</option>
            <option value="18">18</option>
            <option value="19">19</option>
            <option value="20">20</option>
            <option value="21">21</option>
            <option value="22">22</option>
            <option value="23">23</option>
            <!-- Add more hour options as needed -->
          </select>
          :
          <select class="minute-select input-field" bind:value={selectedMinute}>
            <option value="00">00</option>
            <option value="15">15</option>
            <option value="30">30</option>
            <option value="45">45</option>

            <!-- Add more minute options as needed -->
          </select>
        </div>
      </div>
      <div class="price-container">
        <span class="price-label">Price:</span>
        <span class="highlighted-price"
          >{price}€ in Zone {spot.parkingSpotZone.slice(-1)}</span
        >
      </div>
      {#if !spot.occupied}
        <button class="button" on:click={handleReservation}>Reserve Now</button>
      {/if}
      <button class="button" on:click={navigate}>Navigate</button>
      {#if displayAdminActions}
        <label for="zone">Parking Zone:</label>
        <select
          id="zone"
          bind:value={selectedZone}
          on:change={handleZoneChange}
        >
          {#each zoneOptions as zone (zone)}
            <option value={zone}>{zone}</option>
          {/each}
        </select>

        <label for="type">Parking Type:</label>
        <select
          id="type"
          bind:value={selectedType}
          on:change={handleTypeChange}
        >
          {#each typeOptions as type (type)}
            <option value={type}>{type}</option>
          {/each}
        </select>
        <button class="button" on:click={saveChangesAdmin}>Save changes</button>
        <button class="button"><i class="fa-solid fa-chart-simple" /></button>
        <button class="button" on:click={deleteSpot}>Delete parking spot</button
        >
      {/if}
    </div>
  {/if}
</main>

<style>
  main {
    position: absolute;
    z-index: 1;
    bottom: 0;
    width: 100%;
    height: 35%;
    display: flex;
    justify-content: center;
    align-items: flex-end;
  }

  .button {
    border: 2px solid var(--color-primary);
    padding: 5px;
    border-radius: 10px;
    margin: 5px;
    font-family: "Poppins";
    font-size: large;
    background-color: var(--color-accent);
    color: white;
  }

  .cardContainer {
    position: relative;
    z-index: 1;
    height: 300px; /* Set the desired fixed height */
    width: 90%;
    background-color: var(--color-white);
    border-top: 5px solid var(--color-primary);
    border-left: 5px solid var(--color-primary);
    border-right: 5px solid var(--color-primary);
    border-bottom: 5px solid var(--color-primary);
    border-top-right-radius: 20px;
    border-top-left-radius: 20px;
    display: flex;
    align-items: center;
    flex-direction: column;
    overflow-y: auto; /* Enable both mouse and touch scrolling */
  }

  .input-field {
    font-size: 16px; /* Adjust the font size as needed */
    padding: 8px; /* Adjust the padding as needed */
    border: 2px solid var(--color-primary); /* Add a border with your desired color */
    border-radius: 5px; /* Add rounded corners */
    width: 60px; /* Adjust the width as needed */
    margin: 0 5px; /* Add some spacing between the fields */
    background-color: white; /* Set the background color */
    color: var(--color-primary); /* Set the text color */
    outline: none; /* Remove the default outline */
    transition: border-color 0.2s, box-shadow 0.2s; /* Add a transition effect */
  }

  .input-field:focus {
    border-color: var(--color-secondary); /* Change the border color on focus */
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.2); /* Add a subtle box-shadow on focus */
  }

  select {
    width: 50%;
    padding: 10px;
    font-size: large;
    border: 3px solid var(--color-primary);
    border-radius: 10px;
    color: white;
    background-color: var(--color-accent);
    margin-bottom: 5px;
  }

  .spot-title {
    width: 100%;
    height: 70px;
    text-align: center;
    background-color: var(--color-accent);
    height: 50px;
    margin: 0;
    font-size: larger;
    padding: 20px 0px;
    font-weight: 800;
  }
</style>
