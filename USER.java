        public class USER{
            protected String id;
            protected String Username;
            protected String password;


        public USER(String id, String Username , String password){

            this.id = id;
            this.Username = Username;
            this.password = password;
        }
        
        
        public String getId() { return id; }
            public String getUsername() { return Username; }
            public String getPassword() { return password; }

        }