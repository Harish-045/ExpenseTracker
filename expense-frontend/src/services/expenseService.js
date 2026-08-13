import api from "../api/axios";

// CRUD
export const addExpense = (expense) => api.post("/expenses", expense);

export const getAllExpenses = () => api.get("/expenses");

export const getExpenseById = (id) => api.get(`/expenses/${id}`);

export const updateExpense = (id, expense) =>
  api.put(`/expenses/${id}`, expense);

export const deleteExpense = (id) =>
  api.delete(`/expenses/${id}`);

// Pagination
export const getExpensePage = (page, size) =>
  api.get(`/expenses/page?page=${page}&size=${size}`);

// Search
export const searchExpense = (keyword) =>
  api.get(`/expenses/search?keyword=${keyword}`);

// Category Filter
export const getExpenseByCategory = (category) =>
  api.get(`/expenses/category/${category}`);

// Sorting
export const sortExpense = (field) =>
  api.get(`/expenses/sort?field=${field}`);

export const exportExpenses = () =>
    api.get("/expenses/export", {
        responseType: "blob",
    });

export const uploadReceipt = (id, formData) =>
    api.post(`/expenses/${id}/receipt`, formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    });