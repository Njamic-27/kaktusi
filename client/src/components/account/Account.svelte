<script>
  import { redirect } from "@/utils/router/routing";
  import Wallet from "./Wallet.svelte";
  import Reservations from "./Reservations.svelte";
  import { onMount } from "svelte";
  import MessageCard from "../common/MessageCard.svelte";
  import { getUserId, user } from "@/stores/auth";
  import { accountApi, reservationApi } from "@/api";
  import { fade, scale } from "svelte/transition";

  let loaded = false;
  let balance;
  let userId = getUserId();
  let reservations = [];
  let displayMessage = false;
  let message;

  onMount(async () => {
    balance = await accountApi.fetchBalance(userId);
    balance = balance.toFixed(2);
    reservations = await reservationApi.fetchUserReservations(userId);
    loaded = true;
  });

  async function addBalance({ detail: newBalance }) {
    loaded = false;
    try {
      let res = await accountApi.updateBalance(userId, newBalance);
      if (res) {
        redirect("Account");
      } else {
        console.error("Error: Unable to update balance");
      }
    } catch (error) {
      console.error("Error: ", error);
    } finally {
      loaded = true;
    }
  }

  async function handleReservationExtension({
    detail: { reservation, selectedHour },
  }) {
    console.log(reservation);
    loaded = false;
    try {
      let res = await reservationApi.extendReservation(
        reservation.resId,
        selectedHour,
        reservation.parkingSpotId
      );
      if (res) {
        message = "Extended reservation successfully";
        displayMessage = true;
        setTimeout(() => {
          displayMessage = false;
          redirect("Account");
        }, 2000);
      } else {
        message = "Unable to extend reservation, check your balance!";
        displayMessage = true;
        setTimeout(() => {
          displayMessage = false;
        }, 2000);
      }
    } catch (error) {
      console.error("Error: ", error);
    } finally {
      loaded = true;
    }
  }
</script>

{#if displayMessage}
  <MessageCard {message}></MessageCard>
{/if}

<main in:fade out:fade>
  {#if !loaded}
    <div class="loading"></div>
  {:else}
    <Wallet {balance} on:addBalance={addBalance}></Wallet>
    <Reservations
      {reservations}
      on:reservationExtension={handleReservationExtension}
    ></Reservations>
  {/if}
</main>

<style>
  .loading {
    position: absolute;
    top: 50%;
  }
  main {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    width: 100vw;
    margin-top: 8vh;
    margin-bottom: 12vh;
  }
</style>
