<script>
  import { redirect } from "@/utils/router/routing";
  import Wallet from "./Wallet.svelte";
  import Reservations from "./Reservations.svelte";
  import { onMount } from "svelte";
  import account from "@/api/account";
  import { getUserId, user } from "@/stores/auth";

  let loaded = true;
  let balance = 100; //dohvati
  let userId = getUserId();
  let reservations = [1, 2, 3, 4, 5];

  onMount(async () => {
    //balance = account.fetchBalance(userId)
    //reservations = account.fetchReservations(userId)
    balance = 100;
  });

  function addBalance({ detail: newBalance }) {
    loaded = false;
    balance = balance + newBalance;
    //account.updateBalance(userId, balance)
    loaded = true;
    //redirect("Account");
  }
</script>

<main>
  {#if !loaded}
    <div class="loading">Loading</div>
  {:else}
    <Wallet {balance} on:addBalance={addBalance}></Wallet>
    <Reservations {reservations}></Reservations>
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
