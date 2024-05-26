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
  update(id) {
    return `${this.root}/update/${id}`;
  },
  deleteSpot(id) {
    return `${this.root}/delete/${id}`;
  },
  get insert() {
    return this.root + "/insert";
  },
};

const fetchAll = async () => {
  const result = await request.get(urls.fetchAll).then(extractData);
  return result;
};

const insert = async (longitude, latitude, zone, type) => {
  const result = await request
    .get(urls.insert, { longitude, latitude, zone, type })
    .then(extractData);
  return result;
};

const fetchPrice = (id) => {
  const result = request.get(urls.fetchPrice(id)).then(extractData);
  return result;
};

const makeReservation = async (endH, parkingSpotId) => {
  const result = await request.post(urls.makeReservation, {
    endH,
    parkingSpotId,
  });

  return extractData(result);
};

const update = (id, zone, type) => {
  const result = request.put(urls.update(id), { zone, type }).then(extractData);
  return result;
};
const deleteSpot = (id) => {
  const result = request.delete(urls.deleteSpot(id)).then(extractData);
  return result;
};

export default {
  fetchAll,
  fetchPrice,
  makeReservation,
  update,
  deleteSpot,
  insert,
};
