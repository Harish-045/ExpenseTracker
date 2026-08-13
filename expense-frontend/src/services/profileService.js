import api from "../api/axios";

export const getProfile = () =>
    api.get("/profile");

export const updateProfile = (profile) =>
    api.put("/profile", profile);

export const changePassword = (passwordData) =>
    api.put("/profile/password", passwordData);

export const uploadProfileImage = (formData) =>
    api.post("/profile/image", formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    });