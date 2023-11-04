import { extractData } from './helpers';
import request from './request';

const urls = {
  root: '/reservations',
  fetchByUser(id) {
    return `${this.root}/user/${id}`;
  },
  remove(id) {
    return `${this.root}/remove/${id}`;
  },
  updateStatus(id) {
    return `${this.root}/update-status/${id}`;
  },
  get create() {
    return this.root;
  },
  get insertBooking() {
    return `${this.root}/insert-booking`;
  },
  fetchById(id) {
    return `${this.root}/fetchByID/${id}`;
  },
  get fetchBookingsBetween() {
    return `${this.root}/fetchBookingsBetween`
  }
};

const fetchByUser = id => {
  return request.get(urls.fetchByUser(id)).then(extractData);
};

const updateStatus = (id, status) => {
  return request.post(urls.updateStatus(id), { status });
};

const fetchById = id => {
  return request.get(urls.fetchById(id)).then(extractData);
};

const remove = id => {
  return request.post(urls.remove(id));
};

const insertBooking = booking => {
  return request.post(urls.insertBooking, booking).then(extractData);
};



export default {
  fetchByUser,
  updateStatus,
  fetchById,
  remove,
  insertBooking,
};
