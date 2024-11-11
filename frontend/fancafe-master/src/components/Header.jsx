import "../assets/styles/header.scss";
import {Link, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import Category from "../pages/Category";
import {
    faArrowRightFromBracket,
    faArrowRightToBracket,
    faChevronDown,
    faUserPlus
} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUser} from "@fortawesome/free-regular-svg-icons";

const Header = (props) => {

    const [isAuth, setIsAuth] = useState(false)
    const [keyword, setKeyword] = useState()
    const [hover, setHover] = useState(false)
    const navigate = useNavigate();

    useEffect(() => {
        if (localStorage.getItem("accessToken")) {
            setIsAuth(true)
        }
    })
    const handleLogout = () => {
        navigate('/');

        localStorage.removeItem('accessToken');
        props.setReload(!(props.reload));
        setIsAuth(false)
    }

    const searchHandler = (e) => {
        e.preventDefault()
        console.log("search");
        // props.setReload(!(props.reload));
        navigate("/search?keyword=" + keyword)
        props.setReload(!(props.reload));


    }

    const hoverCategory = () => {
        console.log("hoverCategory");
        setHover(true);
    }

    const leaveHeader = () => {
        setHover(false);

    }

    return (<>
            <div className="headerContainer" onMouseLeave={leaveHeader}>
                <div className="headerContainer">
                    <div className="headerContentsContainer">
                        <div className="headerLnCContainer">
                            <Link to="/"><h1 className="logo" onMouseEnter={leaveHeader}>FanCafe</h1></Link>
                            <ul className="headerCatecoryContainer">
                                <Link onMouseEnter={leaveHeader} to="/best">
                                    <li>인기글</li>
                                </Link>
                                <Link onMouseEnter={leaveHeader} to="/new">
                                    <li>전체글</li>
                                </Link>
                                <Link onMouseEnter={hoverCategory} className="category">
                                    <li>카테고리 <FontAwesomeIcon icon={faChevronDown}/></li>
                                </Link>
                            </ul>
                        </div>

                        <form onMouseEnter={leaveHeader} className="searchBar" action="/search"
                              onSubmit={(e) => {
                                  searchHandler(e)
                              }}>
                            <input id="searchInput" type="text"
                                   name="keyword" maxLength="50"
                                   placeholder="글 제목, 본문 검색"
                                   onChange={(e) => {
                                       setKeyword(e.target.value)
                                   }}/>
                            {/*<button onClick={searchHandler}></button>*/}
                        </form>

                        <div className="headerRegisterContainer" onMouseEnter={leaveHeader}>
                            {isAuth ? <>
                                    <Link to="/mypage"><FontAwesomeIcon icon={faUser}/> </Link>
                                    <button className={"logout"} onClick={handleLogout}>로그아웃
                                        <FontAwesomeIcon icon={faArrowRightFromBracket}/>
                                    </button>
                                </>
                                : <><Link to="/join" className={"logout"}>
                                    <FontAwesomeIcon icon={faUserPlus}/>회원가입</Link>
                                    <Link to="/login" className={"logout"}> <FontAwesomeIcon
                                        icon={faArrowRightToBracket}/> 로그인</Link></>}

                        </div>
                    </div>

                </div>
                {hover ? <Category></Category> : null}

            </div>
            {hover ? <div className="background-blocker"></div> : null}
        </>

    )
}

export default Header;