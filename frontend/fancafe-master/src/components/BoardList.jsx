import BoardItem from "./BoardItem";
import WriteButton from "./WriteButton";
import Pagination from "./Pagination";
import {useEffect, useState} from "react";
import axios from "axios";
import {useParams} from "react-router-dom";
import DataNotExist from "./DataNotExist";

const BoardList = (props) => {

    const {category} = useParams();
    let categoryValue;
    if (category == null) {
        categoryValue = "new";
    } else {
        categoryValue = category;
    }

    const [currentPage, setCurrentPage] = useState(1)
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null)
    const [data, setData] = useState();
    // const [total, setData] = useState();


    let url = `http://localhost:8080/${categoryValue}?page=${currentPage}`;

    if (props.keyword) {
        url = `http://localhost:8080/search/title?keyword=${props.keyword}&page=${currentPage}`
    }

    useEffect(() => {
        // console.log(category);
        axios({
            url: url,
            method: "GET",

        }).then((response) => {
            console.log(response.data)
            setData(response.data);
            setLoading(false);
            // console.log(response.data.total);
        }).catch((error) => {
            setError(true);
        })
    }, [currentPage, props.keyword, category])

    useEffect(() => {
        setCurrentPage(1);
    }, [category])


    const handlePageChange = (newData) => {
        setCurrentPage(newData);
    };


    if (loading) {
        return <div>loading</div>
    }
    if (error) {
        return <div>error</div>
    }

    return (<>
            {data && data.posts.map((data) => {
                return (
                    // <BoardItem key={data.id} id={data.id} userId={data.userId} itemTitle={data.title}/>
                    <BoardItem key={data.id} data={data}/>
                );
            })}
            {props.isAuth ? <WriteButton/> : null}
            {data.totalCount == 0 && <DataNotExist/>}
            {data && data.totalCount >= 10 && <Pagination
                totalPost={data.totalCount}
                currentPage={currentPage}
                handlePageChange={handlePageChange}/>
            }
        </>


    )
}

export default BoardList