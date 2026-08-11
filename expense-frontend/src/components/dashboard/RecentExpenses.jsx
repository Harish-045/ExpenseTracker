import {

Paper,

Typography,

Divider,

List,

ListItem,

ListItemText

} from "@mui/material";

function RecentExpenses(){

return(

<Paper

elevation={3}

sx={{

mt:4,

p:3,

borderRadius:4

}}

>

<Typography

variant="h6"

fontWeight="bold"

mb={2}

>

Recent Expenses

</Typography>

<List>

<ListItem>

<ListItemText

primary="Coming in Phase 6"

secondary="Professional Expense Table"

/>

</ListItem>

<Divider/>

</List>

</Paper>

);

}

export default RecentExpenses;