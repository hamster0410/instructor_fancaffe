import BoardList from "../components/BoardList";

const Home = (props) => {
    return (<BoardList isAuth={props.isAuth}></BoardList>)
}

export default Home