/**
 * index.js - Entry Point of the React Application
 * 
 * This is the JavaScript entry point that React uses to render
 * the App component into the DOM.
 * 
 * Key Concepts from Module 5:
 * 
 * 1. ReactDOM.createRoot() - Creates a root for React to render into.
 *    React uses the Virtual DOM, which is an in-memory representation
 *    of the real DOM. When state changes, React compares the Virtual DOM
 *    with the real DOM and updates only the changed parts (reconciliation).
 * 
 * 2. document.getElementById('root') - This targets the <div id="root">
 *    in public/index.html. This is the ONLY real DOM element React needs.
 *    Everything else is rendered by React inside this div.
 * 
 * 3. <React.StrictMode> - A development tool that:
 *    - Highlights potential problems in the application
 *    - Does NOT render any visible UI
 *    - Activates additional checks and warnings
 */
import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
