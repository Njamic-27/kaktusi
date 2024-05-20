<script>
  import { isAdmin } from "@/stores/auth";
  import { redirect } from "@/utils/router/routing";
  const goToMap = () => {
    redirect("Parking");
  };
  let a = isAdmin();
</script>

<main>
  <button on:click={goToMap}>Find parking</button>
  <div class="image-home">
    <img
      src="https://lubricants.goma.hr/wp-content/uploads/sites/3/2023/03/zagreb-1024x288.png"
      alt="ZagrebPanorama"
    />
  </div>
</main>

<style>
  @import url("https://fonts.googleapis.com/css2?family=Dancing+Script&display=swap");

  main {
    position: relative;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 84vh;
    margin-top: 8vh;
    margin-bottom: 8vh;
  }

  button {
    position: absolute;
    bottom: 150px;
    background: var(--color-primary);
    border: 3px solid var(--color-accent);
    border-radius: 15px;
    color: white;
    padding: 5px;
    font-size: larger;
    font-weight: 800;
    width: 70%;
    margin-top: 10px;
    align-self: center;
    justify-self: center;
  }

  .image-home {
    position: absolute;
    display: flex;
    bottom: 0;
    overflow: hidden;
  }

  img {
    width: 100%;
    scale: 1.04;
  }
</style>
