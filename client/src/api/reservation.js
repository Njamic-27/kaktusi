import { extractData } from "./helpers";
import request from "./request";

const urls = {
  root: "/reservations",
  get makeReservation() {
    return this.root + "/make";
  },
  fetchUserReservations(id) {
    return this.root + `/user/${id}`;
  },
  extendReservation(id) {
    return this.root + `/update/${id}`;
  },
};

const makeReservation = async (time, parkingSpotId, userId) => {
  console.log(time, parkingSpotId, userId);
  const result = await request.post(urls.makeReservation, {
    time,
    parkingSpotId,
    userId,
  });

  return extractData(result);
};

const fetchReservations = async (id) => {
  const result = await request
    .get(urls.fetchUserReservations)
    .then(extractData);
  return result;
};

const extendReservation = async (resId, time) => {
  const result = await request
    .put(urls.extendReservation, { resId, time })
    .then(extractData);
  return result;
};

export default {
  makeReservation,
  fetchReservations,
  extendReservation,
};
