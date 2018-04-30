<?php


             header("Content-type: application/json") ;

             $sitename="http://localhost/test/" ;
             $hostname="localhost" ;
             $username="root";
             $password="";

             $database="book_database";

             $tbl_name="selectedbook";



              $records_limit = 4;

    $order_by = "id";



    $connect = @mysqli_connect( $hostname , $username , $password , $database );
    if( $connect )
    {
        @mysqli_query( $connect , "SET CHARACTER SET utf8;" );

        $temp1 = @mysqli_query( $connect , "SELECT COUNT(*) FROM ".$tbl_name );

        $temp2 = @mysqli_fetch_row( $temp1 );

        $total_records = $temp2[0];

        if( isset( $_GET['page'] ) )
        {
            $page = $_GET['page'];
            $offset = $page * $records_limit;
        }
        else
        {
            $page = 0;
            $offset = 0;
        }

        $my_query = "SELECT * FROM ".$tbl_name." ORDER BY ".$order_by.
                    " LIMIT ".$offset." , ".$records_limit;

        $result = @mysqli_query( $connect , $my_query );



        if( $result )
        {
            $response['ads'] = array();
            $response['success'] = 1;

            while( $row = @mysqli_fetch_array( $result ) )
            {
                $ads = array();

                $ads['id']      = $row['id'];
                $ads['title']   = $row['title'];
                $ads['intro']   = $row['intro'];
                $ads['desc']    = $row['description'];
                $ads['image']   = $sitename . $row['image'];
                $ads['seller']  = $row['seller'];
                $ads['email']   = $row['email'];
                $ads['phone']   = $row['phone'];
                $ads['date']    = $row['date'];


                $q = "SELECT name FROM cat WHERE id='".$row['cat_id']."'";
                $temp1 = @mysqli_query( $connect , $q );
                $temp2 = @mysqli_fetch_row( $temp1 );
                $cat = $temp2[0];

                $ads['cat_id']  = $cat;



                array_push( $response['ads'] , $ads );
            }
        }
        else
        {
            $response['success'] = 0;
            $response['message'] = "nothing";
        }

        echo( json_encode( $response ) );

        /* I don't want use JSON_PRETTY_PRINT. If you want, you know! */

        @mysqli_close( $connect );
    }

?>