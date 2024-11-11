import "../assets/styles/category.scss";
import {Link} from "react-router-dom";

const Category = (props) => {

    return (

        <div>
            <div className="category-background"></div>
            <div className="category-container">

                <div className="category-menu">
                    <ul className="category-menu-item">
                        <Link to="/free">
                            <li>🎲 자유게시판</li>
                        </Link>
                        <Link to="/question">
                            <li>❔ 강사님께 질문</li>
                        </Link>
                        <Link to="/ootd">
                            <li>😎 강사님 OOTD</li>
                        </Link>
                        <Link to="/fanart">
                            <li>🖼️ 강사님 팬아트</li>
                        </Link>
                        <Link to="/graduate">
                            <li>🎓 졸업생 커뮤니티</li>
                        </Link>
                        <Link to="/comment">
                            <li>💬 강사님께 한마디</li>
                        </Link>
                        <Link to="/daily">
                            <li>💻 데일리 코테</li>
                        </Link>


                    </ul>


                </div>
            </div>
        </div>
    )
}
export default Category