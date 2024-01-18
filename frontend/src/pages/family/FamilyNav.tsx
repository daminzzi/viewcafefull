import React from 'react';
import { Link } from 'react-router-dom'
import { ReactComponent as HomeIcon } from '../../assets/icons/home.svg';
import { ReactComponent as GalleryIcon } from '../../assets/icons/gallery.svg';
import { ReactComponent as MessageIcon } from '../../assets/icons/message.svg';
import { ReactComponent as VisitIcon } from '../../assets/icons/visit.svg';
import { ReactComponent as ProfileIcon } from '../../assets/icons/profile.svg';

function FamilyNav() {
  return (
    <div className='family-nav'>
      <Link to='/family/home'>
        <HomeIcon className='home-icon'/>
      </Link>
      <Link to='/family/gallery'>
        <GalleryIcon className='gallery-icon'/>
      </Link>
      <Link to='/family/message'>
        <MessageIcon className='message-icon'/>
      </Link>
      <Link to='/family/visit'>
        <VisitIcon className='visit-icon'/>
      </Link>
      <Link to='/family/profile'>
        <ProfileIcon className='profile-icon'/>
      </Link>
    </div>
  )
}

export default FamilyNav