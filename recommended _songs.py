'''
This program is supposed to access the users profile information, find out its top artists/ genres of music, then it will create aplaylist
of songs it recommends the user based on previous sound types
'''

import spotipy
from spotipy.oauth2 import SpotifyOAuth

#specifys the scope of what the program can access from the users profile
scope = "user-top-read playlist-modify-private playlist-modify-public" 

Client_ID = 'Your Client id'
Client_secret = 'Your client secret'
redirect_URI = 'your redirect URI'

#creates access to the users profile and ability to retrieve data and modify it
sp = spotipy.Spotify(auth_manager=SpotifyOAuth(Client_ID,
                                               Client_Secret,
                                               redirect_URI,
                                               scope=scope))

#gets the surrent users id
user_id = sp.current_user()['id']

#checks to see if the playlist already exists (but for some reason doesnt work at the current time being)
def playlist_exists(playlist_name):
    playlists = sp.current_user_playlists()
    for playlist in playlists['items']:
        if playlist['name'] == playlist_name:
            return playlist
    return None

#asks spotify for the current users top artists
top_artists = sp.current_user_top_artists(25, 0, 'short_term')
#from that  it gets the users top artists and top genres from the data
genres = [artist['genres'] for artist in top_artists['items']]
names = [artist['name'] for artist in top_artists['items']]

#Flasttens the 2D list that is the genres list
flatten_genres = sum(genres, [])
most_common = {}
#converts the list into a dictionary of the most common genres of the user
for i in flatten_genres:
    if i not in most_common:
        most_common[i] = 1
    else:
        most_common[i] += 1

#sorts that dictionary in acending order
sorted_dict = sorted(most_common.items(), key = lambda x: x[1])
mc_genres = []
#creates a list of the top 5 most common genres for tyhe user
for n in range(-1, -6, -1):
    mc_genres.append(sorted_dict[n][0])
rec_songs = []
#for every genre the program searches and finds 5 songs that fall under that genre, and add their song ids to the rec_songs list
for genre in mc_genres:
    search_genres = sp.search(genre, 5, 0, 'track', 'US')
    tracks = search_genres.get('tracks', {}).get('items', [])
    for track in tracks:
        rec_songs.append(track['id'])

#checks to see if the playlist already exists, if it doesnt it creates a new playlist (but for some reason just creates a playlist no matter what) 
if not playlist_exists('Recommended Songs'):
    playlist = sp.user_playlist_create(user_id, 'Recommended Songs', False, False, 'Recommended songs for Me')

playlist_id = playlist['id']

#adds all the items to the playlist
sp.playlist_add_items(playlist_id, rec_songs, 0)
