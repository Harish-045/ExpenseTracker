import api from "../api/axios";

export const registerUser = (user) => {
  return api.post("/auth/register", user);
};

export const loginUser = (user) => {
  return api.post("/auth/login", user);
};

export const forgotPassword = (email) => {
  return api.post("/auth/forgot-password", { email });
};

export const resetPassword = (data) => {
  return api.post("/auth/reset-password", data);
};
