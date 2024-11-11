import {useSearchParams} from "react-router-dom";
import BoardList from "../components/BoardList";

const Search = (props) => {
    const [searchParams, setSearchParams] = useSearchParams();


    const keyword = searchParams.get("keyword");
    console.log(keyword);
    return (<div>
            {/*{keyword}*/}
            <div style={{fontSize: "2rem"}}>{keyword}에 대한 검색결과 입니다.</div>
            <BoardList keyword={keyword}></BoardList>
        </div>
    )
}
export default Search