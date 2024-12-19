import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { FaTrash, FaEdit, FaPlus } from 'react-icons/fa'; // Add icon for the create button
import { useNavigate } from 'react-router-dom';  // Import useNavigate for navigation

const RoomManagement = () => {
    const [rooms, setRooms] = useState([]);
    const [filteredRooms, setFilteredRooms] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [roomTypes, setRoomTypes] = useState([]);
    const [filters, setFilters] = useState({ roomType: '', roomNumber: '', roomTypeID: '', status: '' });
    const [confirmDelete, setConfirmDelete] = useState({ isOpen: false, roomId: null });
    const navigate = useNavigate();  // Initialize useNavigate for navigation

    // Fetch all rooms
    const fetchRooms = async () => {
        try {
            setLoading(true);
            const response = await axios.get('http://localhost:8080/api/getAllRoom');
            setRooms(response.data);
            setFilteredRooms(response.data);
        } catch (err) {
            setError('Failed to fetch rooms');
        } finally {
            setLoading(false);
        }
    };

    // Fetch room types
    const fetchRoomTypes = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/roomType');
            setRoomTypes(response.data);
        } catch (err) {
            console.error('Failed to fetch room types');
        }
    };

    // Delete a room
    const deleteRoom = async (id) => {
        try {
            await axios.delete(`http://localhost:8080/api/room/deleteRoom/${id}`, {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('jwt')}`,
                },
            });
            const updatedRooms = rooms.filter((room) => room.id !== id);
            setRooms(updatedRooms);
            setFilteredRooms(updatedRooms);
        } catch (err) {
            alert('Failed to delete room');
        } finally {
            setConfirmDelete({ isOpen: false, roomId: null });
        }
    };

    const handleDeleteClick = (id) => {
        setConfirmDelete({ isOpen: true, roomId: id });
    };

    const handleConfirmDelete = () => {
        if (confirmDelete.roomId) {
            deleteRoom(confirmDelete.roomId);
        }
    };

    const handleCancelDelete = () => {
        setConfirmDelete({ isOpen: false, roomId: null });
    };

    const handleFilterChange = (e) => {
        const { name, value } = e.target;
        setFilters({ ...filters, [name]: value });
    };

    const handleCreateRoom = () => {
        navigate('/AddRoomForRoomTypeDetail');  // Navigate to the create room page
    };

    useEffect(() => {
        const filtered = rooms.filter((room) => {
            const matchRoomType = filters.roomType ? room['Room Type'].toLowerCase().includes(filters.roomType.toLowerCase()) : true;
            const matchRoomNumber = filters.roomNumber ? room['Room number'].toLowerCase().includes(filters.roomNumber.toLowerCase()) : true;
            const matchRoomTypeID = filters.roomTypeID ? String(room['RoomType ID']) === filters.roomTypeID : true;
            const matchStatus = filters.status ? room['Status'].toLowerCase() === filters.status.toLowerCase() : true;
            return matchRoomType && matchRoomNumber && matchRoomTypeID && matchStatus;
        });

        setFilteredRooms(filtered);
    }, [filters, rooms]);

    useEffect(() => {
        fetchRooms();
        fetchRoomTypes();
    }, []);

    if (loading) return <div className="text-center text-blue-500">Loading...</div>;
    if (error) return <div className="text-center text-red-500">{error}</div>;

    return (
        <div className="p-4 max-w-6xl mx-auto">
            <h1 className="text-3xl font-bold mb-6 text-center">Room Management</h1>

            {/* Create Room Button */}
            <div className="flex justify-end mb-6">
                <button
                    onClick={handleCreateRoom}
                    className="bg-green-500 text-white px-6 py-3 rounded-lg shadow hover:bg-green-600 flex items-center"
                >
                    <FaPlus className="mr-2" /> Create Room
                </button>
            </div>

            {/* Filters */}
            <div className="mb-6 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
                <select
                    name="roomTypeID"
                    value={filters.roomTypeID}
                    onChange={handleFilterChange}
                    className="border border-gray-300 px-4 py-2 rounded-lg shadow"
                >
                    <option value="">Filter by Room Type</option>
                    {roomTypes.map((type) => (
                        <option key={type.id} value={type.id}>
                            {type.name}
                        </option>
                    ))}
                </select>
                <input
                    type="text"
                    name="roomType"
                    placeholder="Filter by Room Type Name"
                    value={filters.roomType}
                    onChange={handleFilterChange}
                    className="border border-gray-300 px-4 py-2 rounded-lg shadow"
                />
                <input
                    type="text"
                    name="roomNumber"
                    placeholder="Filter by Room Number"
                    value={filters.roomNumber}
                    onChange={handleFilterChange}
                    className="border border-gray-300 px-4 py-2 rounded-lg shadow"
                />
                <select
                    name="status"
                    value={filters.status}
                    onChange={handleFilterChange}
                    className="border border-gray-300 px-4 py-2 rounded-lg shadow"
                >
                    <option value="">Filter by Status</option>
                    <option value="available">Available</option>
                    <option value="booked">Booked</option>
                </select>
            </div>

            {/* Room Cards */}
            <div className="grid grid-cols-1 gap-6 md:grid-cols-2 lg:grid-cols-3">
                {filteredRooms.map((room) => (
                    <div key={room.id} className="bg-white rounded-lg shadow-lg p-6">
                        <div className="flex items-center justify-between mb-4">
                            <h2 className="text-xl font-semibold">{room['Room Type']}</h2>
                            <div className="flex space-x-2">
                                <button onClick={() => handleDeleteClick(room.id)} className="text-red-600 hover:text-red-800">
                                    <FaTrash />
                                </button>
                            </div>
                        </div>
                        <p className="text-gray-600">Room Number: {room['Room number']}</p>
                        <p className="text-gray-600">Quantity of Bed: {room['Quantity of bed']}</p>
                        <p className="text-gray-600">Status: {room['Status']}</p>
                    </div>
                ))}
            </div>

            {confirmDelete.isOpen && (
                <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
                    <div className="bg-white p-6 rounded shadow-md">
                        <h2 className="text-lg font-semibold mb-4">Confirm Deletion</h2>
                        <p className="mb-4">Are you sure you want to delete this room?</p>
                        <div className="flex justify-end space-x-4">
                            <button
                                onClick={handleCancelDelete}
                                className="bg-gray-300 text-black px-4 py-2 rounded-lg hover:bg-gray-400"
                            >
                                Cancel
                            </button>
                            <button
                                onClick={handleConfirmDelete}
                                className="bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600"
                            >
                                Delete
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default RoomManagement;
