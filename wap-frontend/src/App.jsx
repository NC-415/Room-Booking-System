import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap.bundle.min.js"
import AddApartment from './components/apartment/AddApartment'
import ExistingApartments from "./components/apartment/ExistingApartments"
import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import EditApartment from "./components/apartment/EditApartment"
import Home from "./components/home/Home"
import NavBar from "./components/layout/NavBar"
import ApartmentListing from "./components/apartment/ApartmentListing"
import FindBooking from "./components/booking/FindBooking"
import Login from "./components/Authorization/Login"

function App() {
  return (
    <>
      <main>
        <Router>
        <NavBar />
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/find-booking" element={<FindBooking />} />
            <Route path="/browse-all-rooms" element={<ApartmentListing/>} />
            <Route path="/" element={<Home />} />
            <Route path="/edit-apartment/:apartmentId" element={<EditApartment />} />
            <Route path="/existing-apartments" element={<ExistingApartments />} />
            <Route path="/add-apartment" element={<AddApartment />} />
          </Routes>
        </Router>
      </main>
    </>
  )
}

export default App
