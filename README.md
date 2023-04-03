# NewsApp
- fetches news from an API https://newsapi.org/ and show them in a RecyclerView.
- support sign in and sign up on Firebase. If not registered, some function won't work.
- support recover email.
- support validation fields.
- supports searching news.
- supports choose news from 4 countries.
- supports choose categories of news.
- supports save, delete Article.
- support move and swipe Article.
- supports day&night mode.
- support animation.
- support notifications every 15 minutes by work manager.
- supports check internet connections.

# Preview
![collect](https://user-images.githubusercontent.com/108537240/213318988-72368091-3a0c-4b4a-bbaa-6ed5ac82d9c9.jpg)
![collect 3](https://user-images.githubusercontent.com/108537240/214073396-fea7bbda-b595-4e53-8f16-392327809db1.jpg)
![5](https://user-images.githubusercontent.com/108537240/187301558-16fbb29f-9603-466e-825d-c6ae428f28f4.jpg)

# Libraries And Technologies used
- Clean Architecture: Data, Domain, Presentation.
- Coroutines : Executing some code in the background.
- Room Database : Save,delete and get the articles into a local database.
- Navigation Component : Navigate between fragments.
- Dagger hilt : Dependency injection.
- Firebase Authentication 
- Use Case: For business logic of project.
- Mapper: For separate entities.
- MVVM + StateFlow : Android architecture used to separate logic code from ui and save the
  application state in case the configuration changes.
- Retrofit + Gson Converter: Fetch news from rest api as a gson file and convert it to a kotlin
  object.
- Okhttp client. Work with Interceptor.
- RecyclerView with DiffUtil.
- Pagination with Paging 3, Retrofit and kotlin Flow.
- Work Manager.
- Glide : Catch and cache images from the internet and show them in an imageView.
- Coil : Work with image.
- viewBinding : to access the views without needing to inflate them.
- Unit tests.

![4](https://user-images.githubusercontent.com/108537240/218298056-4f011f01-1603-4e95-9fa1-013ec5c601a6.jpg)
