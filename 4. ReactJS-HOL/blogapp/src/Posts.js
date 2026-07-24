import React from 'react';
import Post from './Post';

class Posts extends React.Component {
    // Step 5: Initialize component with a list of Post in state
    constructor(props){
        super(props);
        this.state = {
            posts: []
        };
    }

    // Step 6: loadPosts() method - Fetch posts from JSONPlaceholder API
    loadPosts() {
        fetch('https://jsonplaceholder.typicode.com/posts')
            .then(response => response.json())
            .then(data => {
                let postList = data.map(item => new Post(item.id, item.title, item.body));
                this.setState({ posts: postList });
            });
    }

    // Step 7: componentDidMount() hook - calls loadPosts()
    componentDidMount() {
        this.loadPosts();
    }

    // Step 8: render() method - display title and body of posts
    render() {
        return (
            <div>
                <h1>Blog Posts</h1>
                {this.state.posts.map(post => (
                    <div key={post.id}>
                        <h2>{post.title}</h2>
                        <p>{post.body}</p>
                        <hr />
                    </div>
                ))}
            </div>
        );
    }

    // Step 9: componentDidCatch() - display errors as alert messages
    componentDidCatch(error, info) {
        alert('An error occurred: ' + error.message);
        console.log('Error Info:', info);
    }
}

export default Posts;
