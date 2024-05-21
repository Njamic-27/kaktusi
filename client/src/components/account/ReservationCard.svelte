<script>
  export let reservation;
  let startTime = 3;
  let address = "Gundulićeva 18";
  //let currentHour = new Date().getHours();
  //let currentMinute = new Date().getMinutes()
  let currentHour = 4;
  let currentMinute = 0;
  let duration = 1;
  let infoText = startTime > currentHour ? "Starts in" : "Expires in";
  let infoTime =
    startTime > currentHour
      ? `${startTime - currentHour}h`
      : `${startTime + duration - currentHour}h`;

  if (infoTime == "1h" && currentMinute != 0) {
    infoTime = `${60 - currentMinute}min`;
  }
</script>

<main>
  <div class="reservationContainer">
    <span>Address: {address}</span>
    <span>Start time: {startTime}h</span>
    <span>Duration: {duration}h</span>
    <span>{infoText}: {infoTime}</span>
  </div>
</main>

<style>
  .reservationContainer {
    background-color: var(--color-accent);
    height: 100px;
    padding: 15px;
    border-radius: 15px;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
  }
</style>
