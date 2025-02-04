import React from 'react';
import MainHeader from "../layout/MainHeader"
import RoomSearch from "../common/RoomSearch"
import RoomCarousel from "../common/RoomCarousel"
import Parallax from "../common/HotelService"
import HotelService from '../common/HotelService';

const Home = () => {

  const message = location.state && location.state.message
  return (
    <section>
    {message && <p className="text-warning px-5">{message}</p>}
      <MainHeader />
      <div className="container">
        <RoomSearch />
        <RoomCarousel />
        <Parallax />
        <RoomCarousel />
        <HotelService />
        <Parallax />
        <RoomCarousel />
      </div>
    </section>
  );
};

export default Home;