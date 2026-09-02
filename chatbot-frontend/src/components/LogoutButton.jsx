import { useNavigate } from "react-router-dom";
import { logout } from "../services/authService";
import "../pages/auth.css";
function LogoutButton() {

    const navigate = useNavigate();

    const handleLogout = () => {

        logout();

        navigate("/login", {
            replace: true
        });
    };

    return (
        <button
            className="logout-button"
            onClick={handleLogout}
        >
            Logout
        </button>
    );
}

export default LogoutButton;