import "../assets/styles/board.scss"
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCommentDots, faHeart} from "@fortawesome/free-regular-svg-icons";
import {Link} from "react-router-dom";
import {useModifyTime} from "../utils/useModifyTime";

const BoardItem = ({data}) => {
    const modifiedTime = useModifyTime(data.createdDate)

    const categoryTrimmer = (category) => {
        switch (category) {
            case "free":
                return "자유"
            case "question":
                return "질문"
            case "ootd":
                return "ootd"
            case "fanart":
                return "팬아트"
            case "graduate":
                return "졸업생"
            case "comment":
                return "한마디"
            case "daily":
                return "데일리코테"
            default:
                return "-"
        }
    }

    return (
        <div className={"list-container"}>
            <Link to={`/${data.category}/${data.id}`}>
                <div className={"info"}>
                    <div className={"title"}>
                        <span> {data.title} </span>
                        <div className={"comment-count"}>
                            <FontAwesomeIcon icon={faCommentDots}/>
                            <span>{data.count_comment}</span>
                        </div>
                    </div>

                    <div className={"detail-info"}>
                        <div className={"like-info"}>
                            <div>
                                {/*<FontAwesomeIcon icon={faFaceGrinTears}/>*/}
                                <FontAwesomeIcon icon={faHeart}/>
                                <span> {data.count_heart} </span>
                            </div>
                            <span>·</span>
                            <Link to={'/' + data.category}><span
                                className="category"> {categoryTrimmer(data.category)} </span></Link>

                        </div>
                        <div className={"author-info"}>
                            <span> {data.nickname} </span>
                            <span>·</span>
                            <span>조회수 {data.hits}</span>
                            <span>·</span>
                            <span>{modifiedTime}</span>
                        </div>

                    </div>

                </div>
            </Link>
        </div>
    )

}

export default BoardItem