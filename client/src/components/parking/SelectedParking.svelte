<script>
  import { fade, fly, scale, slide } from "svelte/transition";
  import { createEventDispatcher, onMount } from "svelte";
  import { parkingApi, reservationApi } from "@/api";
  import { isAdmin } from "@/stores/auth";
  import { redirect } from "@/utils/router/routing";
  import OptionButton from "../common/OptionButton.svelte";
  import MessageCard from "../common/MessageCard.svelte";
  import { authStore } from "@/stores";

  export let spot;

  let address = "";
  $: displayCard = false;
  let displayMessage = false;
  let price = 0;
  let totalPrice = 0;
  let displayAdminActions = false;
  let displayTotalPrice = false;
  let message;
  let userId = authStore.getUserId();

  let selected = [false, false, false];

  const zoneOptions = ["1", "2", "3", "4"];
  const typeOptions = ["NORMAL", "HANDICAP", "ELECTRIC"];

  let currentHour = new Date().getHours();
  let availableHours = Array.from(
    { length: 24 - currentHour - 1 },
    (_, i) => i + currentHour + 1
  );
  let selectedHour;

  let dispatch = createEventDispatcher();
  let selectedZone = spot.parkingSpotZone;
  let selectedType = spot.parkingSpotType;

  let lat = spot.latitude;
  let lng = spot.longitude;
  onMount(async () => {
    displayCard = false;

    const nominatimUrl = `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lng}`;

    const id = spot.id;
    price = spot.parkingSpotZone.price;

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
    totalPrice = (price * selectedHour).toFixed(2);
    displayTotalPrice = true;
  }

  function navigate() {
    const url = `https://www.google.com/maps?q=${lat},${lng}`;
    window.location.href = url;
  }

  const saveChangesAdmin = async () => {
    await parkingApi.update(spot.id, selectedZone - 1, selectedType);
    dispatchRefresh();
  };

  const deleteSpot = async () => {
    let res = parkingApi.deleteSpot(spot.id);
    if (res) dispatchRefresh();
  };

  async function handleReservation() {
    displayCard = false;
    let parkingSpotId = spot.id;

    let response = await reservationApi.makeReservation(
      selectedHour,
      parkingSpotId,
      userId
    );

    if (response) {
      displayCard = false;
      message = "Successful reservation";
      displayMessage = true;
      setTimeout(() => {
        displayMessage = false;
        redirect("Account");
      }, 2000);
    } else {
      message = "Failed reservation, check your balance";
      displayMessage = true;
      setTimeout(() => {
        displayMessage = false;
      }, 2000);
    }
  }

  const dispatchRefresh = async () => {
    dispatch("refresh");
  };

  function handleHourSelection({ detail: hour }) {
    selectedHour = hour;
    calculateTotalPrice();
    toggleClickedButton(hour);
  }

  function toggleClickedButton(value) {
    selected = [false, false, false];
    selected[value - 1] = true;
  }
</script>

{#if displayMessage}
  <MessageCard {message}></MessageCard>
{/if}

<main>
  {#if displayCard === true}
    <div class="cardContainer" in:slide={{ delay: 200 }} out:slide>
      <div class="spot-title">{address}</div>
      {#if !displayAdminActions}
        <p class="parking-type">Parking type: {spot.parkingSpotType}</p>
        {#if !spot.occupied}
          <div class="time-container">
            <span class="time-label">Reserve for:</span>
            <div class="timeSelectGrid">
              <div class="option">
                <OptionButton
                  value="1"
                  selected={selected[0]}
                  on:hourSelection={handleHourSelection}
                ></OptionButton>
              </div>
              <div class="option">
                <OptionButton
                  value="2"
                  selected={selected[1]}
                  on:hourSelection={handleHourSelection}
                ></OptionButton>
              </div>
              <div class="option">
                <OptionButton
                  value="3"
                  selected={selected[2]}
                  on:hourSelection={handleHourSelection}
                ></OptionButton>
              </div>
            </div>
          </div>
          <div class="price-container">
            <span class="price-label"
              >Price per hour in Zone {spot.parkingSpotZone.id + 1}:</span
            >
            <span class="highlighted-price">{price}€</span>
          </div>
          {#if displayTotalPrice}
            <div class="price-container" in:scale>
              <span class="price-label" in:fly>Total price:</span>
              <span class="highlighted-price" in:fly>{totalPrice}€</span>
            </div>
            <button class="button" on:click={handleReservation} in:fly
              >Reserve Now</button
            >
          {/if}

          <button class="button" on:click={navigate}>Navigate</button>
        {:else}
          <div class="takenSpotContainer">
            <div class="message">This spot is occupied</div>
          </div>
        {/if}
      {/if}
      {#if displayAdminActions}
        <label for="zone">Parking Zone:</label>
        <select
          id="zone"
          bind:value={selectedZone}
          on:change={handleZoneChange}
        >
          {#each zoneOptions as zone (zone)}
            <option value={zone}>Zone {zone}</option>
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
        <button class="button" on:click={deleteSpot}>Delete parking spot</button
        >
      {/if}
    </div>
  {/if}
</main>

<style>
  main {
    position: absolute;
    z-index: 5;
    bottom: 0;
    width: 100%;
    height: 35%;
    display: flex;
    justify-content: space-around;
    align-items: flex-end;
  }

  .takenSpotContainer {
    height: 100%;
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    font-size: 1.3rem;
  }

  .option {
    width: 100%;
    text-align: center;
  }

  .price-container {
    margin-top: 5px;
  }

  .timeSelectGrid {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    justify-items: center;
    width: 100%;
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
    z-index: 10;
    height: 400px; /* Set the desired fixed height */
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
    width: 97%;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
  }

  select {
    width: 50%;
    padding: 10px;
    font-size: large;
    border: 3px solid var(--color-primary);
    border-radius: 10px;
    color: white;
    background-color: var(--color-primary);
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
