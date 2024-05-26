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
  let balance; //dohvati
  let userId = getUserId();
  let reservations = [];
  let displayMessage = false;
  let message;

  onMount(async () => {
    balance = await accountApi.fetchBalance(userId);
    reservations = await reservationApi.fetchUserReservations(userId);
    loaded = true;
  });

  async function addBalance({ detail: newBalance }) {
    loaded = false;
    balance = balance + newBalance;
    try {
      let res = await accountApi.updateBalance(userId, newBalance);
      if (res) {
        balance = await accountApi.fetchBalance(userId);
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
    loaded = false;
    try {
      let res = await reservationApi.extendReservation(
        reservation.resId,
        selectedHour
      );
      if (res) {
        message = "Extended reservation successfully";
        displayMessage = true;
        setTimeout(() => {
          displayMessage = false;
          redirect("Account");
        }, 2000);
      } else {
        console.error("Error: Unable to extend reservation");
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

<main in:scale out:scale>
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
