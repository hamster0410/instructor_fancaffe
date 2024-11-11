import "../assets/styles/dataNotExist.scss";
import {faFolderOpen} from "@fortawesome/free-regular-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const DataNotExist = () => {
    return (<>

        <div className="main-content">
            <FontAwesomeIcon className="icon" icon={faFolderOpen}/>

            <p>해당하는 게시물이 없습니다.</p>

        </div>
    </>)
}
export default DataNotExist