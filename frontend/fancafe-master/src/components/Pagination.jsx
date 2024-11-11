import "../assets/styles/pagination.scss";
import {useEffect, useState} from "react";

const Pagination = ({currentPage, totalPost, handlePageChange}) => {

    console.log("totalPost: ", totalPost);

    const [page, setPage] = useState(1); // 현재 페이지 수

    // const totalPost = 100; // 총 게시물 수
    const pageRange = 10; // 페이지당 보여줄 게시물 수
    const btnRange = 10; // 보여질 페이지 버튼의 개수

    const currentSet = Math.ceil(page / btnRange); // 현재 버튼이 몇번째 세트인지 나타내는 수
    const startPage = (currentSet - 1) * btnRange + 1; // 현재 보여질 버튼의 첫번째 수

    const endPage = startPage + btnRange - 1; // 현재 보여질 끝 버튼의 수
    const totalSet = Math.ceil(Math.ceil(totalPost / pageRange) / btnRange); // 전체 벼튼 세트 수

    const pages = Math.ceil(totalPost / pageRange);
    useEffect(() => {
    }, []);

    return (
        <>
            {console.log(currentPage)}
            <div>
                <div className="pagination">
                    <button disabled={startPage === 1} onClick={() => setPage(startPage - 1)}>
                        {"<"}
                    </button>

                    {Array(btnRange).fill(startPage).map((_, i) => {
                        return (
                            ((pages) >= (startPage + i)) &&
                            <button
                                className={(startPage + i === currentPage) ? "numBtn selected" : "numBtn"}
                                key={i}
                                onClick={() => handlePageChange(startPage + i)}>
                                {startPage + i}

                            </button>

                        );
                    })}

                    <button disabled={totalPost / 10 <= endPage} onClick={() => setPage(endPage + 1)}>
                        {">"}

                    </button>

                </div>


            </div>
        </>
    );
};

export default Pagination;