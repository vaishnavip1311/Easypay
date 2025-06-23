import React from 'react';
import './Profile.css';

function Profile() {
    return (
        <div style={{fontFamily: 'Inter, sans-serif',backgroundColor: '#f3f4f6',display: 'flex',justifyContent: 'center',
            alignItems: 'flex-start',minHeight: '100vh',padding: '20px',boxSizing: 'border-box',width: '100%',}}>
            <div style={{maxWidth: '80rem',width: '100%',margin: '0 auto',padding: '1rem',}}>
                <div style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>
                    <div className="flex flex-col lg:flex-row gap-6">
                        {/* Profile Picture Card */}
                        <div className="w-full lg:w-1/3 profile-card mb-3">
                            <h2
                                style={{fontSize: '1.25rem',fontWeight: '600',color: '#2d3748',marginBottom: '1.5rem',width: '100%',textAlign: 'left',}}>
                                Profile Picture
                            </h2>
                            <div
    style={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        marginBottom: '1.5rem',
    }}
>
    <div className="profile-text-circle">
        <img
            src="/images/vaishnavi.jpeg" // Replace with your actual image URL
            alt="Profile"
            className="profile-image"
        />
    </div>
</div>

                        </div>

                        {/* Account Details Card */}
                        <div className="w-full lg:w-2/3 account-card">
                            <div
                                style={{display: 'flex',justifyContent: 'space-between',alignItems: 'center',marginBottom: '1.5rem',}}>
                                <h2
                                    style={{fontSize: '1.25rem',fontWeight: '600',color: '#2d3748',}}>
                                    Account Details
                                </h2>
                                <button type="button" className="edit-button">
                                    Edit
                                </button>
                            </div>

                            <div>
                                {/* Username */}
                                <div className="mb-2">
                                    <label className="form-label">
                                        Username{' '}
                                        <span style={{color: '#718096',fontWeight: 'normal',}}>
                                            (how your name will appear to other users on the site)
                                        </span>
                                    </label>
                                    <div className="display-detail">username</div>
                                </div>

                                {/* First and Last Name */}
                                <div className="grid-cols-1 md:grid-cols-2">
                                    <div>
                                        <label className="form-label">First name</label>
                                        <div className="display-detail">Valerie</div>
                                    </div>
                                    <div>
                                        <label className="form-label">Last name</label>
                                        <div className="display-detail">Luna</div>
                                    </div>
                                </div>

                                {/* Organization and Location */}
                                <div className="grid-cols-1 md:grid-cols-2">
                                    <div>
                                        <label className="form-label">Organization name</label>
                                        <div className="display-detail">Start Bootstrap</div>
                                    </div>
                                    <div>
                                        <label className="form-label">Location</label>
                                        <div className="display-detail">San Francisco, CA</div>
                                    </div>
                                </div>

                                {/* Email */}
                                <div className="m2-5">
                                    <label className="form-label">Email address</label>
                                    <div className="display-detail">name@example.com</div>
                                </div>

                                {/* Phone and Birthday */}
                                <div
                                    className="grid-cols-1 md:grid-cols-2"
                                    style={{ marginBottom: '2rem' }}
                                >
                                    <div>
                                        <label className="form-label">Phone number</label>
                                        <div className="display-detail">555-123-4567</div>
                                    </div>
                                    <div>
                                        <label className="form-label">Birthday</label>
                                        <div className="display-detail">06/10/1988</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Profile;
