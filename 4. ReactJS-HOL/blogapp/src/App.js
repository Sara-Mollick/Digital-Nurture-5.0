import React from 'react';
import Posts from './Posts';

// Step 10: Add the Posts component to App component
class App extends React.Component {
  render() {
    return (
      <div className="App">
        <Posts />
      </div>
    );
  }
}

export default App;
