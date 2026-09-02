import LogoutButton from "./LogoutButton";


function Navbar({ onMenuClick }) {

    return (

        <header className="navbar">

            {/* =========================================
                LEFT SIDE
            ========================================= */}

            <div className="navbar-left">

                <button
                    className="menu-button"
                    onClick={onMenuClick}
                    aria-label="Open menu"
                >
                    ☰
                </button>

                <div className="navbar-title">
                    DocGPT
                </div>

            </div>


            {/* =========================================
                AI STATUS
            ========================================= */}

            <div className="navbar-status">
                <span className="status-dot">
                    ●
                </span>

                AI Ready
            </div>


            {/* =========================================
                RIGHT SIDE
            ========================================= */}

            <div className="navbar-right">

                <LogoutButton />

            </div>

        </header>
    );
}


export default Navbar;