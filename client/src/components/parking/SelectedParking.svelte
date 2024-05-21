<script>
  import { fade, fly, slide } from "svelte/transition";
  import { createEventDispatcher, onMount } from "svelte";
  import { parkingApi } from "@/api";
  import { isAdmin } from "@/stores/auth";
  import { redirect } from "@/utils/router/routing";

  export let spot;
  let address = "";
  $: displayCard = false;
  let displayMessage = false;
  let price = 0;
  let totalPrice = 0;
  let displayAdminActions = false;
  let displayTotalPrice = false;
  let message;

  let currentHour = new Date().getHours();
  let availableHours = Array.from(
    { length: 24 - currentHour - 1 },
    (_, i) => i + currentHour + 1
  );

  let selectedZone = spot.parkingSpotZone;
  let selectedType = spot.parkingSpotType;

  if (isAdmin()) {
    displayAdminActions = true;
  }

  function handleZoneChange(event) {
    selectedZone = event.target.value;
  }

  function handleTypeChange(event) {
    selectedType = event.target.value;
  }

  function calculateTotalPrice() {
    totalPrice = price * (selectedHour - currentHour);
    displayTotalPrice = true;
  }

  const zoneOptions = ["Zone1", "Zone2", "Zone3", "Zone4"];
  const typeOptions = ["NORMAL", "HANDICAP", "ELECTRIC"];

  let selectedHour = "00";

  const lat = spot.latitude;
  const lng = spot.longitude;

  const nominatimUrl = `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lng}`;
  onMount(async () => {
    displayCard = false;

    const id = spot.id;
    price = await parkingApi.fetchPrice(id);

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
    dispatchRefresh();
  };

  const deleteSpot = async () => {
    let res = parkingApi.deleteSpot(spot.id);
    if (res) dispatchRefresh();
  };

  async function handleReservation() {
    displayCard = false;
    let endH = selectedHour;
    let parkingSpotId = spot.id;
    //let response = await parkingApi.makeReservation(endH, parkingSpotId);
    let response = false;
    //provjera sa balance, ima li dovoljno
    if (response) {
      displayCard = false;
      message = "Successful reservation"
      displayMessage = true;
      setTimeout(() => {
        displayMessage = false;
        redirect("Account");
      }, 2000);
    } else {
      message = "Failed reservation, check your balance"
      displayMessage = true;
      setTimeout(() => {
        displayMessage = false;
      }, 2000);
    }
  }

  const dispatchRefresh = async () => {
    dispatch("refresh");
  };

  const getStats = async () => {
    const res = await parkingApi.fetchStats(spot.id);
  };
</script>

<main>
  {#if displayMessage}
    <div class="messageCard" in:fade out:fade>{message}</div>
  {/if}
  {#if displayCard === true}
    <div class="cardContainer" in:slide={{ delay: 200 }} out:slide>
      <div class="spot-title">{address}</div>
      <p class="parking-type">Parking type: {spot.parkingSpotType}</p>
      <div class="time-container">
        <span class="time-label">End Hour:</span>
        <div class="time-select">
          <select
            class="hour-select input-field"
            bind:value={selectedHour}
            on:change={calculateTotalPrice}
          >
            {#each availableHours as hour}
              <option value={hour.toString().padStart(2, "0")}
                >{hour.toString().padStart(2, "0")}</option
              >
            {/each}
          </select>
        </div>
      </div>
      <div class="price-container">
        <span class="price-label"
          >Price per hour in Zone {spot.parkingSpotZone.slice(-1)}:</span
        >
        <span class="highlighted-price">{price}€</span>
      </div>
      {#if displayTotalPrice}
        <div class="price-container">
          <span class="price-label">Total price:</span>
          <span class="highlighted-price">{totalPrice}€</span>
        </div>
      {/if}
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
        <button class="button" on:click={getStats}
          ><i class="fa-solid fa-chart-simple" /></button
        >
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

  .messageCard {
    position: absolute;
    font-size: larger;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    width: 250px;
    height: 100px;
    background-color: var(--color-primary);
    border-radius: 15px;
    padding: 20px;
    color: white;
    z-index: 1;
    top: 0;
  }

  .button {
    border: 2px solid var(--color-primary);
    padding: 5px;
    border-radius: 10px;
    margin: 5px;
    font-family: "Poppins";
    font-size: large;
    background-color: var(--color-primary);
    color: white;
  }

  .cardContainer {
    position: relative;
    z-index: 1;
    height: 350px; /* Set the desired fixed height */
    width: 95%;
    background-color: var(--color-accent);
    border-top-right-radius: 20px;
    border-top-left-radius: 20px;
    display: flex;
    align-items: center;
    flex-direction: column;
    overflow-y: auto;
  }

  .time-container {
    width: 60%;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }

  .input-field {
    font-size: 16px;
    padding: 4px;
    border: 2px solid var(--color-primary);
    border-radius: 5px;
    width: 100px;
    margin: 0 5px;
    background-color: white;
    color: var(--color-primary);
    outline: none;
    transition:
      border-color 0.2s,
      box-shadow 0.2s;
  }

  .input-field:focus {
    border-color: var(--color-secondary);
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
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
    height: 80px;
    text-align: center;
    background-color: var(--color-primary);
    font-size: larger;
    font-weight: 800;
    color: white;
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>
