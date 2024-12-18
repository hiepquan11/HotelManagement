import React, { useEffect, useState } from 'react';
import axios from 'axios';

const RoomManagement = () => {
    const [rooms, setRooms] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [confirmDelete, setConfirmDelete] = useState({ isOpen: false, roomId: null });

    // Fetch all rooms
    const fetchRooms = async () => {
        try {
            setLoading(true);
            const response = await axios.get('http://localhost:8080/api/getAllRoom');
            setRooms(response.data);
        } catch (err) {
            setError('Failed to fetch rooms');
        } finally {
            setLoading(false);
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
            setRooms(rooms.filter((room) => room.id !== id));
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

    useEffect(() => {
        fetchRooms();
    }, []);

    if (loading) return <div className="text-center text-blue-500">Loading...</div>;
    if (error) return <div className="text-center text-red-500">{error}</div>;

    return (
        <div className="p-4 max-w-4xl mx-auto">
            <h1 className="text-2xl font-bold mb-4 text-center">Room Management</h1>
            <table className="w-full border-collapse border border-gray-300">
                <thead>
                    <tr className="bg-gray-100">
                        <th className="border border-gray-300 px-4 py-2">ID</th>
                        <th className="border border-gray-300 px-4 py-2">Room Type</th>
                        <th className="border border-gray-300 px-4 py-2">Room Number</th>
                        <th className="border border-gray-300 px-4 py-2">Quantity of Bed</th>
                        <th className="border border-gray-300 px-4 py-2">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {rooms.map((room) => (
                        <tr key={room.id} className="text-center">
                            <td className="border border-gray-300 px-4 py-2">{room.id}</td>
                            <td className="border border-gray-300 px-4 py-2">{room['Room Type']}</td>
                            <td className="border border-gray-300 px-4 py-2">{room['Room number']}</td>
                            <td className="border border-gray-300 px-4 py-2">{room['Quantity of bed']}</td>
                            <td className="border border-gray-300 px-4 py-2">
                                <button
                                    onClick={() => handleDeleteClick(room.id)}
                                    className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
                                >
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            {confirmDelete.isOpen && (
                <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
                    <div className="bg-white p-6 rounded shadow-md">
                        <h2 className="text-lg font-semibold mb-4">Confirm Deletion</h2>
                        <p className="mb-4">Are you sure you want to delete this room?</p>
                        <div className="flex justify-end space-x-4">
                            <button
                                onClick={handleCancelDelete}
                                className="bg-gray-300 text-black px-4 py-2 rounded hover:bg-gray-400"
                            >
                                Cancel
                            </button>
                            <button
                                onClick={handleConfirmDelete}
                                className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
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
