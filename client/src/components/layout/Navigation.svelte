<script>
  import Button from "@/components/common/Button.svelte";
  import { redirect } from "@/utils/router/routing";
  import { isAdmin } from "@/stores/auth";
</script>

<nav>
  <section class="container" />

  <section class="mobile-container">
    <Button on:click={() => redirect("Home")} inline
      ><i class="fa-solid fa-house" /></Button
    >
    <Button on:click={() => redirect("Parking")} inline
      ><i class="fa-solid fa-square-parking" /></Button
    >
    {#if isAdmin()}
      <Button on:click={() => redirect("CreateSpot")} inline
        ><i class="fa-solid fa-upload" /></Button
      >
    {/if}
  </section>
</nav>

<style>
  .container {
    display: none;
  }

  .mobile-container {
    position: fixed;
    bottom: 0vh;
    left: 0vh;
    display: flex;
    justify-content: space-around;
    align-items: center;
    width: 100%;
    height: 7vh;
    border-top: 1vh solid var(--color-accent);
    background-color: var(--color-primary);
    z-index: 3;
  }

  .mobile-container > :global(button) {
    font-size: 1.5rem;
    color: var(--color-white);
  }

  @media only screen and (min-width: 768px) {
    .container {
      position: fixed;
      top: 8vh;
      display: block;
      width: 20%;
      height: 92vh;
      border-right: 3px solid var(--color-secondary);
      background-color: var(--color-primary);
    }

    .mobile-container {
      display: none;
    }
  }
</style>
