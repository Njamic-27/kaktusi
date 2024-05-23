<script>
  import { redirect } from "@/utils/router/routing";
  import Wallet from "./Wallet.svelte";
  import Reservations from "./Reservations.svelte";
  import { onMount } from "svelte";
  import MessageCard from "../common/MessageCard.svelte";
  import { getUserId, user } from "@/stores/auth";
  import { accountApi, reservationApi } from "@/api";

  let loaded = true;
  let balance = 100; //dohvati
  let userId = getUserId();
  let reservations = [1, 2, 3, 4, 5, 6, 7];
  let displayMessage = false;
  let message;

  onMount(async () => {
    //balance = accountApi.fetchBalance(userId)
    //reservations = reservationApi.fetchReservations(userId)
    balance = 100; //kasnije maknuti
  });

  async function addBalance({ detail: newBalance }) {
    loaded = false;
    balance = balance + newBalance;
    try {
      //let res = await accountApi.updateBalance(userId, balance);
      let res = true;
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
    loaded = false;
    try {
      /* let res = await reservationApi.extendReservation(
        reservation,
        selectedHour
      ); */
      let res = true;
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

<main>
  {#if !loaded}
    <div class="loading">Loading</div>
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
