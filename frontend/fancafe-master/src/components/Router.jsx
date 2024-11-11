import Header from "./Header";
import Footer from "./Footer";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Home from "../pages/Home";
import Join from "../pages/Join";
import Login from "../pages/Login";
import PostWrite from "../pages/PostWrite";
import JoinSuccess from "../pages/JoinSuccess";
import "../assets/styles/router.scss";
import MyPage from "../pages/MyPage";
import ContentsViewer from "./ContentsViewer";
import Search from "../pages/Search";

const Router = (props) => {
    return (
        <BrowserRouter>
            <div className="outer-container">
                <Header reload={props.reload} setReload={props.setReload}/>
                <div className="inner-container">
                    <Routes>
                        <Route path="/" element={<Home isAuth={props.isAuth}/>}/>
                        <Route path="/:category" element={<Home isAuth={props.isAuth}/>}/>

                        {/*<Route path="/item/:itemId" element={<ItemDetail/>}/>*/}
                        {/*<Route path="/test" element={<Test/>}/> /!*라우터 사용 예시*!/*/}
                        {/*<Route path="/best" element={<Best/>}/>*/}
                        {/*<Route path="/new" element={<New/>}/>*/}
                        <Route path="/join" element={<Join/>}/>
                        <Route path="/login" element={<Login reload={props.reload} setReload={props.setReload}/>}/>
                        <Route path="/post/write" element={<PostWrite/>}/>
                        <Route path="/join/success" element={<JoinSuccess/>}/>
                        <Route path="/login" element={<Login/>}/>
                        <Route path="/mypage" element={<MyPage/>}/>
                        <Route path="/:category/:post_id" element={<ContentsViewer/>}/>
                        <Route path="/search" element={<Search/>}/>

                    </Routes>
                </div>

                {/*{props.isAuth ? <WriteButton/> : null}*/}
                <Footer/>

            </div>
        </BrowserRouter>

    )
}

export default Router