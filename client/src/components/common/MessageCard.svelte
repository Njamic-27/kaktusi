<script>
  import { fade } from "svelte/transition";

  export let message;
</script>

<main>
  <div class="messageCard" in:fade out:fade>{message}</div>
</main>

<style>
  .messageCard {
    position: absolute;
    font-size: larger;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    width: 70vw;
    height: 100px;
    background-color: var(--color-primary);
    border-radius: 15px;
    padding: 5vw;
    color: white;
    z-index: 999;
    top: 60%;
    left: 10vw;
  }
</style>
