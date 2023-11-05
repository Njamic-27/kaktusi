import { extractData } from "./helpers";
import request from "./request";

const urls = {
  root: "/parking",
  get fetchAll() {
    return this.root + "/all";
  },
  fetchPrice(id) {
    return `${this.root}/price/${id}`;
  },
  get makeReservation() {
    return this.root + "/reserve";
  },
};

const fetchAll = async () => {
  const result = await request.get(urls.fetchAll).then(extractData);
  return result;
};

const fetchPrice = (id) => {
  const result = request.get(urls.fetchPrice(id)).then(extractData);
  return result;
};

const makeReservation = async (endH, endM, parkingSpotId) => {
  const result = await request
    .post(urls.makeReservation, { endH, endM, parkingSpotId })
    .then(extractData);
  console.log("RES " + result);
  return result;
};
export default {
  fetchAll,
  fetchPrice,
  makeReservation,
};
