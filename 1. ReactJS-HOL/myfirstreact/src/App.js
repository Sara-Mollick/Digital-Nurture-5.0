/**
 * App.js - Main Application Component (MODIFIED AS PER EXERCISE)
 * 
 * Module 5 Exercise:
 * ------------------
 * The exercise asks to replace the default create-react-app content
 * with a simple component that displays:
 *    "Welcome the first session of React"
 * 
 * Key React Concepts Demonstrated:
 * 
 * 1. COMPONENT: App is a functional component (a JavaScript function
 *    that returns JSX). React applications are built by composing
 *    components together.
 * 
 * 2. JSX (JavaScript XML): The HTML-like syntax inside the return
 *    statement is JSX. It looks like HTML but is actually JavaScript.
 *    Babel transpiles JSX into React.createElement() calls.
 *    Example: <h1>Hello</h1> becomes React.createElement('h1', null, 'Hello')
 * 
 * 3. VIRTUAL DOM: When this component renders, React creates a Virtual
 *    DOM representation first, then efficiently updates the real DOM.
 *    This is faster than directly manipulating the DOM.
 * 
 * 4. SPA (Single Page Application): This component is rendered inside
 *    the single <div id="root"> in index.html. The browser never
 *    loads a new HTML page — React dynamically updates the content.
 */

function App() {
  return (
    <h1>Welcome the first session of React</h1>
  );
}

export default App;
