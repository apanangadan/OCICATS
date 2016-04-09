

def write_excel_from_tweet_df(tweets_df, path, sheet_name = "tweets"):
   """
   Simple function to write a dataframe of tweet_structs to an Excel file.

   Args:
      tweets_df: Tweet_struct dataframe.
      path: Path for the Excel file to be written.
      sheet_name: Name of the sheet to be written.
   """
   writer = pd.ExcelWriter(path, engine="xlsxwriter", datetime_format="yyyy-mm-dd hh:mm", options={'strings_to_urls': False})
   tweets_df.to_excel(writer, sheet_name=sheet_name, encoding='utf-8')
   writer.save()

def write_excel_from_dict_tweet_df(tweets_df_dict, path):
   """
   Simple function to write a dictionary of dataframes of tweet_structs to an Excel file.

   Each dataframe will be written to a separate sheet. The sheets will be named based on the key name.

   Args:
      tweets_df_dict: Dictionary of tweet_struct dataframes.
      path: Path for the Excel file to be written.
   """
   writer = pd.ExcelWriter(path, engine="xlsxwriter", datetime_format="yyyy-mm-dd hh:mm", options={'strings_to_urls': False})
   for term in tweets_df_dict:
      tweets_df_dict[term].to_excel(writer, sheet_name=str(term), encoding='utf-8')
   writer.save()

def read_excel_to_tweet_df_dict(path):
   """
   Simple function to read an Excel file into a tweet_struct dataframe.

   Args:
      path: Path for the Excel file to be read.
   """
   tweets_file = pd.ExcelFile(path)
   return {term : tweets_file.parse(term, converters={"tweet_id" : str, "user_id" : str}) for term in tweets_file.sheet_names}