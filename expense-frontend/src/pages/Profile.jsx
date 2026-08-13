import { useEffect, useState } from "react";
import DashboardLayout from "../layouts/DashboardLayout";
import {
    getProfile,
    updateProfile,
    changePassword,
    uploadProfileImage
} from "../services/profileService";
import "../styles/profile.css";
import { toast } from "react-toastify";
import Loader from "../components/common/Loader";

function Profile() {

    const [profile, setProfile] = useState({
        name: "",
        email: ""
    });
    const [loading, setLoading] = useState(true);
    const [image, setImage] = useState(null);

    const [password, setPassword] = useState({
        currentPassword: "",
        newPassword: ""
    });

    useEffect(() => {

        loadProfile();

    }, []);

   const loadProfile = async () => {

       setLoading(true);

       try {

           const response = await getProfile();

           setProfile(response.data);

       } catch (error) {

           console.log(error);

       } finally {

           setLoading(false);

       }

   };
    const handleProfileChange = (e) => {

        setProfile({

            ...profile,

            [e.target.name]: e.target.value

        });

    };

    const handlePasswordChange = (e) => {

        setPassword({

            ...password,

            [e.target.name]: e.target.value

        });

    };

    const updateUser = async (e) => {

        e.preventDefault();

        try {

            await updateProfile(profile);

            toast.success("Profile Updated Successfully");

        }

        catch (error) {

            console.log(error);

        }

    };

    const updateUserPassword = async (e) => {

        e.preventDefault();

        try {

            await changePassword(password);

            toast.success("Password Changed Successfully");

            setPassword({

                currentPassword: "",
                newPassword: ""

            });

        }

        catch (error) {

            console.log(error);

        }


    };
    const uploadImage = async () => {

        if (!image) return;

        const formData = new FormData();

        formData.append("file", image);

        await uploadProfileImage(formData);

        loadProfile();

    };
   if (loading) {

       return (
           <DashboardLayout>
               <Loader />
           </DashboardLayout>
       );

   }
    return (

        <DashboardLayout>

            <div className="profile-container">

                <h2>My Profile</h2>

                <div className="profile-card">

                   <div className="avatar">

                       {profile.profileImage ? (

                           <img
                               src={`http://13.234.67.184:8080/uploads/${profile.profileImage}`}
                               alt="Profile"
                           />

                       ) : (

                           profile.name
                               ? profile.name.charAt(0).toUpperCase()
                               : "U"

                       )}

                   </div>
                    <div className="image-upload">

                        <input
                            type="file"
                            onChange={(e) => setImage(e.target.files[0])}
                        />

                    </div>
                    <button
                        type="button"
                        className="save-btn"
                        onClick={uploadImage}
                    >
                        Upload Image
                    </button>

                    <form onSubmit={updateUser}>

                        <div className="form-group">

                            <label>Name</label>

                            <input
                                type="text"
                                name="name"
                                value={profile.name}
                                onChange={handleProfileChange}
                            />

                        </div>


                        <div className="form-group">

                            <label>Email</label>

                            <input
                                type="email"
                                name="email"
                                value={profile.email}
                                onChange={handleProfileChange}
                            />

                        </div>

                        <button className="save-btn">

                            Update Profile

                        </button>

                    </form>

                </div>

                <div className="profile-card">

                    <h3>Change Password</h3>

                    <form onSubmit={updateUserPassword}>

                        <div className="form-group">

                            <label>Current Password</label>

                            <input
                                type="password"
                                name="currentPassword"
                                value={password.currentPassword}
                                onChange={handlePasswordChange}
                            />

                        </div>

                        <div className="form-group">

                            <label>New Password</label>

                            <input
                                type="password"
                                name="newPassword"
                                value={password.newPassword}
                                onChange={handlePasswordChange}
                            />

                        </div>

                        <button className="save-btn">

                            Change Password

                        </button>

                    </form>

                </div>

            </div>

        </DashboardLayout>

    );

}

export default Profile;
